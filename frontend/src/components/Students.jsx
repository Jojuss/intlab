import Student from "../models/Student";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useState, useEffect } from 'react';
import DataService from '../services/DataService';
import CatalogSC from "./CatalogSC.jsx";
export default function Students(props) {
    const headers = [
        {name: 'surname', label: "Фамилия"}, 
        {name: 'name', label: "Имя"}, 
        {name: 'phoneNumber', label: "Номер телефона"}, 
        {name: 'categoriesString', label: 'Элективы'}
    ];
    const nameCatalog = "Студенты";
    const [items, setItems] = useState([]);
    const url = '/student';
    const requestParams = '?name=nameData&surname=surnameData&phoneNumber=phoneNameData';
    const [data, setData] = useState(new Student());
    const [isEditing, setEditing] = useState(false);
    useEffect(() => {
        loadItems();
    }, []);


    function loadItems() {
        DataService.readAll(url, (data) => new Student(data))
            .then(data => setItems(data));
    }

    function handleAdd() {
        DataService.create(url +requestParams
            .replace("nameData", data.name)
            .replace("surnameData", data.surname)
            .replace("phoneNameData", data.phoneNumber))
            .then(() => loadItems()); 
    }

    function handleEdit(editedId) {
        DataService.read(url + "/" + editedId, (e) => new Student(e))
        .then(data => {
            setData(new Student(data));
        });
        setEditing(true);
        
    }
    function handleEditIsDone() {

        DataService.update(url + "/" + data.id + requestParams
        .replace("nameData", data.name)
        .replace("surnameData", data.surname)
        .replace("phoneNameData", data.phoneNumber)).then(() => loadItems());
        
    }

    function handleDelete(item) {
        if (window.confirm('Удалить выбранный элемент?')) {
            const promises = [];
            promises.push(DataService.delete(url + "/" + item));
            Promise.all(promises).then(() => {
                loadItems();
            });
        }
    }

    // при каждом изменении поля формы происходит вызов этого метода, обновляя данные объекта
    function handleFormChange(event) {
        setData({ ...data, [event.target.name]: event.target.value });
    }

    // определяет действия формы по нажатию Отправить
    function submitForm() {     
        if (!isEditing) {
            // если добавление элемента
            handleAdd();
        } else {
            // если редактирование элемента;
            handleEditIsDone();
            setEditing(false);
        }
    }
    // вызывается при закрытии модального окна или по нажатию кнопки Добавить и очищает данные объекта
    function reset() {        
        setData(new Student());
        setEditing(false);
    }

     // для каждого типа сущности своя форма, 
    // которая передается дальше в абстрактный компонент Catalog в качестве props.form
    const form = <Form onSubmit={submitForm}>
                    <Form.Group className="mb-3" controlId="name">
                        <Form.Label>Фамилия</Form.Label>
                        <Form.Control name="surname" value={data.surname} type="input" placeholder="Enter text" onChange={handleFormChange} required/>
                        <Form.Label>Имя</Form.Label>
                        <Form.Control name="name" value={data.name} type="input" placeholder="Enter text" onChange={handleFormChange} required/>
                        <Form.Label>Номер телефона</Form.Label>
                        <Form.Control name="phoneNumber" value={data.phoneNumber} type="input" placeholder="Enter text" onChange={handleFormChange} required/>
                    </Form.Group>              
                    <Button variant="primary" type="submit">
                        Отправить
                    </Button>
                </Form>

    return <div className="container-lg pt-5 min-vh-100">
        <CatalogSC name={nameCatalog}
                    headers={headers} 
                    items={items} 
                    onAdd={handleAdd}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    onClose={reset}
                    onBtnAdd={reset}
                    form={form}>
        </CatalogSC>
    </div>
}