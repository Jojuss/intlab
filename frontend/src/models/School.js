import Student from "./Student";

export default class School {
    constructor(data) {
        this.id = data?.id;      
        this.name = data?.name || '';
        this.students = data?.students.map((e) => new Student(e)) || '';
        this.countStudents = '';
        if (this.students !== '') {
            this.countStudents = this.students.length;
        }
    }
}