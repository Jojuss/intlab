import DataService from '../services/DataService';
import Student from '../models/Student';
import Category from '../models/Category';
import { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import School from '../models/School';
import { Link} from "react-router-dom";
export default function ReportStudentCategory(props) {
    const headersEmp = [
        {name: 'surname', label: "Фамилия"},
        {name: 'name', label: "Имя"},
        {name: 'categoriesString', label: 'Категории'},
        {name: 'phoneNumber', label: "Номер телефона"},
    ];


    const url = "/student/category?cat=id"
    const urlCat = '/category';

    const [itemsCat, setItemsCat] = useState([]);

    const [itemsStud, setItemsStud] = useState([]);

    useEffect(() => {
        loadItemsCtegories();
    });

    function loadItemsCtegories() {
        DataService.readAll(urlCat, (data) => new Category(data))
            .then(data => setItemsCat(data));
    }

    function loadItemsStudents(selectedCategory) {
        DataService.readAll(url.replace("id", selectedCategory), (data) => new Student(data))
            .then(data => setItemsStud(data));
    }

    return <div className="container-lg pt-5 min-vh-100">
        <h1>Отчет</h1>

        <Form.Select onChange={(e) => {loadItemsStudents(e.target.value)}} aria-label="Default select example">
            <option selected disabled>Выберите категорию</option>
            {
                itemsCat.map((e) => <option key={`stud_${e.id}`} value={`${e.id}`}>{`${e.name}`}</option>)
            }
        </Form.Select>

        <div >
            <table className={`table table-hover`}>
                <thead>
                <tr>
                    {
                        headersStud.map((header) => <th key={header.name}>{header.label}</th>)
                    }
                    <th key='company'>Автошкола</th>
                </tr>
                </thead>
                <tbody>
                {
                    itemsStud.map((item) => <tr key={item.id}>
                        {
                            headersStud.map((header) => <td key={`${header.name}_${item.id}`}>{item[header.name]}</td>)
                        }
                        <td key={`ds_${item.id}`}><Link to={`/school/${item['school'].id}`}>{item['school'].name}</Link></td>
                    </tr>)
                }
                </tbody>
            </table>
        </div>
    </div>
}