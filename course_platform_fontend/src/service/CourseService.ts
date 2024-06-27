import { Course } from "../types/Course";
import { getMethod } from "./api";

class CourseService {
    private static courseService : CourseService;
    
    constructor() {

    }
    public static getInstance() : CourseService {
        if(!this.courseService){
            this.courseService = new CourseService();
        }
        return this.courseService;
    }
    async getCourses() : Promise<Course[]> {
        let data : Course[]= []
        try {
            const response = await getMethod("/courses");
            if(response && response.status == 200){
                data = response.data.result;
            }
        } catch (error) {
            console.log(error);
            
        }
        return data;
    }
}
export default CourseService;