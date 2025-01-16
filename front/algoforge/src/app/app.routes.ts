// src/app/app-routing.module.ts

import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { TaskListComponent } from './features/tasks/task-list/task-list.component';
import { TaskCreateComponent } from './features/tasks/task-create/task-create.component';
import { TaskDetailComponent } from './features/tasks/task-detail/task-detail.component';
import { TaskEditComponent } from './features/tasks/task-edit/task-edit.component';
import { TaskSearchComponent } from './features/tasks/task-search/task-search.component';

import { ContestListComponent } from './features/contests/contest-list/contest-list.component';
import { ContestCreateComponent } from './features/contests/contest-create/contest-create.component';
import { ContestEditComponent } from './features/contests/contest-edit/contest-edit.component';
import { ContestTasksComponent } from './features/contests/contest-tasks/contest-tasks.component';
import { MyContestsComponent } from './features/contests/my-contests/my-contests.component';
import { ContestResultsComponent } from './features/contests/contest-results/contest-results.component';

import { AdminDashboardComponent } from './features/admin/admin-dashboard/admin-dashboard.component';
import { CategoriesComponent } from './features/admin/categories/categories.component';

import { SolutionsListComponent } from './features/solutions/solutions-list/solutions-list.component';
import { SolutionDetailComponent } from './features/solutions/solutions-detail/solutions-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'tasks', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: 'solutions', component: SolutionsListComponent, canActivate: [AuthGuard] },
  { path: 'solutions/:id', component: SolutionDetailComponent, canActivate: [AuthGuard] }, // Новый маршрут

  {
    path: 'tasks',
    children: [
      { path: '', component: TaskListComponent },
      { path: 'search', component: TaskSearchComponent },
      { path: 'create', component: TaskCreateComponent, canActivate: [AuthGuard] },
      { path: ':id', component: TaskDetailComponent },
      { path: ':id/edit', component: TaskEditComponent, canActivate: [AuthGuard] },
    ]
  },

  {
    path: 'contests',
    children: [
      { path: '', component: ContestListComponent },
      { path: 'my', component: MyContestsComponent, canActivate: [AuthGuard] },
      { path: 'create', component: ContestCreateComponent, canActivate: [AuthGuard] },
      { path: ':id/edit', component: ContestEditComponent, canActivate: [AuthGuard] },
      { path: ':id/tasks', component: ContestTasksComponent, canActivate: [AuthGuard] },
      { path: ':id/results', component: ContestResultsComponent },
    ]
  },

  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] },
    children: [
      { path: 'categories', component: CategoriesComponent },
    ]
  },

  { path: '**', redirectTo: '' }
];
