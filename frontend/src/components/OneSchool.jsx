import School from "../models/School";
import { useState, useEffect } from 'react';
import { Link, useParams } from "react-router-dom";
import DataService from '../services/DataService';
import Student from '../models/Student';
import Category from '../models/Category';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ModalForm from './commons/ModalForm';
import styles from "./OneSchool.module.css";

export default function OneSchool(props) {
    const { id } = useParams();

    const url = '/school/id';
    const urlSchoolStud = '/school/id/students';
    const urlStud = '/student/free';
    const urlCat = '/category';
    const urlHire = '/school/id/hire';
    const urlDismiss = '/school/id/dismiss';
    const urlAddCat = '/student/id/addCat';
    const urlDelCat = '/student/id/delCat';
    const requestParamsCategory = '?category=catId';
    const requestParamsHire = '?studentId=studId';

    const [school, setSchool] = useState(new School());
    const [itemsStudFree, setItemsStudFree] = useState([]);
    const [itemsStudSchool, setItemsStudSchool] = useState([]);
    const [itemsCat, setItemsCat] = useState([]);

    const headersStud = [
        {name: 'surname', label: "Фамилия"}, 
        {name: 'name', label: "Имя"}, 
        {name: 'categoriesString', label: 'Категории'},
        {name: 'phoneNumber', label: "Номер телефона"}
    ];

    useEffect(() => {      
        loadSchool();
    }, []);

    function loadSchool() {
        DataService.read(url.replace("id", id), (data) => new School(data)).then((data) => setSchool(new School(data)));
    }

    function loadItemsStudents() {
        DataService.readAll(urlStud, (data) => new Student(data))
            .then(data => setItemsStudFree(data));
    }
    
    function loadItemsCategories() {
        DataService.readAll(urlCat, (data) => new Category(data))
            .then(data => setItemsCat(data));
    }

    function loadItemsStudentsSchool() {
        DataService.readAll(urlSchoolStud.replace("id",  school.id), (data) => new Student(data))
            .then(data => setItemsStudSchool(data));
    }


    // логика для найма или увольнения
    const [isShowHire, setShowHire] = useState(false);

    function showModalFormHire() {  
        loadItemsStudents();
        setShowHire(true);
    }
    function unshowModalFormHire() {
        setShowHire(false);
    }

    const [isShowDismiss, setShowDismiss] = useState(false);

    function showModalFormDismiss(e) { 
        loadItemsStudentsSchool();
        setShowDismiss(true);
    }
    function unshowModalFormDismiss() {
        setShowDismiss(false);
    }
    const [student, setStudent] = useState(new Student());

    function studentChosenFree(e) {
        setStudent(itemsStudFree.filter((stud) => stud.id == e.target.value)[0]);
    }

    function studentChosenBusy(e) {
        setStudent(Array.from(school.students).filter((stud) => stud.id == e.target.value)[0]);
    }

    function formHireSubmit() { 
        DataService.update(urlHire.replace("id",school.id) + requestParamsHire
                        .replace("studId", student.id))
                        .then(() => loadSchool());
         
                    
        setStudent(new Student());
    }

    function formDismissSubmit() {
        DataService.update(urlDismiss.replace("id",school.id) + requestParamsHire
            .replace("studId", student.id))
            .then(() => loadSchool());
        
        setStudent(new Student());
    }

    const [isShowChooseCategory, setShowChooseCategory] = useState(false);

    function showModalFormChooseCategory() {
        loadItemsCategories();
        loadItemsStudentsSchool();
        setShowChooseCategory(true);
    }
    function unshowModalFormChooseCategory() {
        setShowChooseCategory(false);
        setCategoryStud([]);
    }

    const [categoriesChosen, setCategoryStud] = useState([]);
    function checkBoxChanged(e) {
        // если чекбокс был выбран, то добавляем в массив категорию
        if (e.target.checked) 
        {
            categoriesChosen.push(e.target.value);
        } // если чекбокс был убран, то исключаем его значение из массива
        else 
        {
            let i = categoriesChosen.indexOf(e.target.value);
            if(i >= 0) {
                categoriesChosen.splice(i,1);
            }            
        }
    }

    function formChooseCategoriesSubmit() {
        for (let i = 0; i < categoriesChosen.length; i++) {
            DataService.update(urlAddCat.replace("id",student.id) + requestParamsCategory
                        .replace("catId", categoriesChosen[i]))
                        .then(() => loadSchool());
        }
        
        let categoriesStud = student.categories;
     
        for (let i = 0; i < categoriesStud.length; i++) {
            if (categoriesChosen.indexOf(''+categoriesStud[i].id) == -1) {
                // удаление категории
                DataService.update(urlDelCat.replace("id",student.id) + requestParamsCategory
                        .replace("catId", categoriesStud[i].id))
                        .then(() => loadSchool());
            }
        }
    }

    const formHire = <Form onSubmit={formHireSubmit}>
                        <Form.Group className="mb-3" controlId="student">
                            <Form.Label>Студент</Form.Label>
                            <Form.Select name="student_select" type="input"  onChange={studentChosenFree} required>
                            <option selected disabled>Выберите студента</option>
                            {
                                itemsStudFree.map((e) => <option key={`stud_${e.id}`} value={`${e.id}`}>{`${e.surname} ${e.name}`}</option>)
                            } 
                            </Form.Select>                 
                        </Form.Group>    
                    <Button variant="primary" type="submit">
                        Отправить
                    </Button>
                    </Form>;


     const formDismiss = <Form onSubmit={formDismissSubmit}>
                        <Form.Group className="mb-3" controlId="student">
                            <Form.Label>Студент</Form.Label>
                            <Form.Select name="student_select" type="input" onChange={studentChosenBusy} required>
                            <option selected disabled>Выберите студента</option>
                            {
                                itemsStudSchool.map((e) => <option key={`stud_${e.id}`} value={`${e.id}`}>{`${e.surname} ${e.name}`}</option>)
                            } 
                            </Form.Select>                 
                        </Form.Group>                          
                    <Button variant="primary" type="submit">
                        Отправить
                    </Button>
                    </Form>;

    const formCheckBoxesCategory = <Form onSubmit={formChooseCategoriesSubmit}>
          <Form.Group className="mb-3" controlId="student">
                <Form.Label>Студент</Form.Label>
                <Form.Select name="student_select" type="input" onChange={studentChosenBusy} required>
                    <option selected disabled>Выберите студента</option>
                    {
                        itemsStudSchool.map((e) => <option key={`stud_${e.id}`} value={`${e.id}`}>{`${e.surname} ${e.name}`}</option>)
                    } 
                </Form.Select>                 
        </Form.Group>  
        <Form.Group className="mb-3" controlId="category">
            <Form.Label>Категории</Form.Label>
            <div className={`${styles.prokrutka}`}>
                {
                    itemsCat.map((p) => <div style={{width: `150 px`}}>
                        <input type="checkbox"  key={`${p.id}`} value={`${p.id}`} onChange={checkBoxChanged}/>
                        {/* {!studentHasCategory(p) && <input type="checkbox"  key={`${p.id}`} value={`${p.id}`} onChange={checkBoxChanged}/>}
                        {studentHasCategory(p) && <input type="checkbox"  key={`${p.id}`} value={`${p.id}`} checked onChange={checkBoxChanged}/>} */}
                        {`${p.name}`}
                        </div>)
                }  
            </div>    
        </Form.Group>  
        <Button variant="primary" type="submit">
                Отправить
        </Button>
    </Form>

    if (!school) {
        return <div><h1>Автошкола не найдена</h1>
                <Link to='/Schools'>
                    <Button variant="info">Назад</Button>
                </Link>
        </div>
    }
    return <div className="container-lg pt-5 min-vh-100">
        <Link to='/Schools'>
            <Button variant="info">Назад</Button>
        </Link>
        <h1>Название: {school.name}</h1>
        <h2>Количество студентов: {school.countStudents}</h2>
        <Button name="Зачисление" onClick={showModalFormHire} variant="btn btn-outline-success">Зачислить студента</Button>
        <Button name='Отчисление' onClick={showModalFormDismiss} variant="btn btn-outline-danger">Отчислить студента</Button>
        <Button name='Выбор категории' onClick={showModalFormChooseCategory} variant="btn btn-outline-primary">Выбор категории</Button>
        <div >
            <table className={`table table-hover`}>
            <thead>
                    <tr>
                        {
                        headersStud.map((header) => <th key={header.name}>{header.label}</th>)
                        }
                    </tr>
            </thead>
            <tbody>
                    {
                    Array.from(school.students).map((item, index) => <tr key={item.id}>
                         {
                            headersStud.map((header) => <td key={`${header.name}_${item.id}`}>{item[header.name]}</td>)
                        }
                    </tr>)
                    }
            </tbody>
        </table>
        </div>
        <ModalForm show={isShowHire} onClose={unshowModalFormHire} modalTitle={"Зачисление"} form={formHire}></ModalForm>
        <ModalForm show={isShowDismiss} onClose={unshowModalFormDismiss} modalTitle={"Отчисление"} form={formDismiss}></ModalForm>
        <ModalForm show={isShowChooseCategory} onClose={unshowModalFormChooseCategory} modalTitle={"Управление категориями"} form={formCheckBoxesCategory}></ModalForm>
  </div>

}