import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SaveCompleteService } from '../../services/save-complete.service';
import { ClusteringService } from '../../services/clustering.service';

interface ClusterStudent {
  name: string;
  age: number;
  gender: string;
  bmi: number;
}

@Component({
  selector: 'app-group-student',
  templateUrl: './group-student.component.html',
  styleUrls: ['./group-student.component.scss']
})
export class GroupStudentComponent implements OnInit {
  classId: number = 0;
  studentData: any[] = [];
  clusteredStudentData: any[] = [];

  // Predefined BMI Categories
  private BMI_CATEGORIES = [
    { name: 'Underweight', range: [0, 16], color: 'bg-primary' },
    { name: 'Healthy', range: [16.1, 23.3], color: 'bg-success' },
    { name: 'Overweight', range: [23.4, 26.7], color: 'bg-warning' },
    { name: 'Obese', range: [26.8, Infinity], color: 'bg-danger' }
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private saveCompleteService: SaveCompleteService,
    private clusteringService: ClusteringService
  ) { }

  ngOnInit(): void {
    // Getting classId from route
    this.classId = this.activatedRoute.snapshot.params['classId'];
    this.getStudentCompleteDataByClassId(this.classId);
  }

  // Fetch student data by class ID
  getStudentCompleteDataByClassId(classId: any): void {
    this.saveCompleteService.getStudentCompleteDataByClassId(classId).subscribe({
      next: (response: any) => {
        this.studentData = response.data;
        this.clusterStudents();
      },
      error: (error: any) => {
        console.error('Error fetching student data:', error);
      }
    });
  }

  // Cluster students using K-Means and analyze clusters
  clusterStudents(): void {
    const clusteringRequest = {
      students: this.studentData.map((student): ClusterStudent => ({
        name: student.name,
        age: student.age,
        gender: student.gender.toLowerCase(),
        bmi: student.bmi
      })),
      k: 4,
      maxIterations: 100
    };

    this.clusteringService.clusterStudents(clusteringRequest).subscribe({
      next: (response: any) => {
        this.clusteredStudentData = response.data.clusters;
        this.analyzeAndMergeClusters();
      },
      error: (error: any) => {
        console.error('Error clustering students:', error);
      }
    });
  }

  // Analyze clusters, assign names/colors, and merge duplicate groups
  analyzeAndMergeClusters(): void {
    if (!this.clusteredStudentData || this.clusteredStudentData.length === 0) return;

    // Map clusters to BMI categories
    const categorizedClusters = this.clusteredStudentData.map((group: any[]) => {
      const avgBMI = this.calculateAverageBMI(group);
      const category = this.BMI_CATEGORIES.find(c => avgBMI >= c.range[0] && avgBMI < c.range[1]);

      return {
        groupData: group,
        categoryName: category?.name || 'Unknown',
        categoryColor: category?.color || 'bg-secondary'
      };
    });

    // Merge clusters with the same category
    const mergedClusters: any[] = [];
    categorizedClusters.forEach(cluster => {
      const existingGroup = mergedClusters.find(group => group.categoryName === cluster.categoryName);
      if (existingGroup) {
        // Merge student data
        existingGroup.groupData = [...existingGroup.groupData, ...cluster.groupData];
      } else {
        // Add as a new group
        mergedClusters.push(cluster);
      }
    });

    this.clusteredStudentData = mergedClusters;
  }

  // Calculate average age for a group
  calculateAverageAge(group: any[]): number {
    if (group.length === 0) return 0;
    const totalAge = group.reduce((sum, student) => sum + student.age, 0);
    return totalAge / group.length;
  }

  // Calculate average BMI for a group
  calculateAverageBMI(group: any[]): number {
    if (group.length === 0) return 0;
    const totalBMI = group.reduce((sum, student) => sum + student.bmi, 0);
    return totalBMI / group.length;
  }
}
