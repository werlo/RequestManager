export class Task{

    id?: number;
    
    name?: string
    
    description?: string;
    
    deadline?: Date;

    estimatedEffort?: number; // in hours
    
    orderNum?: number;
    
	status?: TaskStatus;

    Task(){

    }
    
}

export enum TaskStatus{
    QUEUED,
    PENDING,
    IN_PROGRESS,
    COMPLETED

}