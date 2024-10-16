export default class GroupedStudAndCategoryDto {
    constructor(data) {
        this.Id = data?.Id;
        this.categoryName = data?.categoryName;
        this.studentsCount = data?.studentsCount;
    }
}