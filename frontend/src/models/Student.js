import Category from "./Category";

export default class Student {
    constructor(data) {
        this.id = data?.id;
        this.surname = data?.surname || '';
        this.name = data?.name || '';
        this.phoneNumber = data?.phoneNumber || '';
        this.categories = data?.categories.map((p) => new Category(p)) || '';
        this.categoriesString = '';
        if (this.categories !== '') {
            this.categories.forEach((p) => {this.categoriesString += p.name + " "});
        }    
        this.drivingSchool = data?.drivingSchool || '';
    }
}

Student.prototype.equals = function (obj){
    if(typeof obj != typeof this)
        return false;
    if (this.id === obj.id) {
        return true;
    }
    return false;
}