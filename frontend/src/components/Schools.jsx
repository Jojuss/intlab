import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useState, useEffect } from 'react';
import DataService from '../services/DataService';
import Catalog from "./Catalog.jsx";
import School from "../models/School";
import ModalForm from './commons/ModalForm';
import { useNavigate } from "react-router-dom";


export default function Schools(props) {
    const headers = [
        {name: 'name', label: "Название"}, 
        {name: 'countStudents', label: "Студенты"},
    ];
    const nameCatalog = "Автошколы";

    const url = '/school';
    const requestParams = '?name=nameData';

    const [items, setItems] = useState([]);
    

    const [data, setData] = useState(new School());
    const [isEditing, setEditing] = useState(false);
    useEffect(() => {
        loadItems();
    }, []);

    const [selectedId, setSelectedId] = useState(null);

    function loadItems() {
        DataService.readAll(url, (data) => new School(data))
            .then(data => setItems(data));
    }

    

    function handleAdd() {
        DataService.create(url +requestParams
            .replace("nameData", data.name))
            .then(() => loadItems()); 
    }

    function handleEdit(editedId) {
        DataService.read(url + "/" + editedId, (e) => new School(e))
        .then(data => {
            setData(new School(data));
        });
        setEditing(true);
        
    }
    function handleEditIsDone() {

        DataService.update(url + "/" + data.id + requestParams
        .replace("nameData", data.name))
        .then(() => loadItems());
        
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
        setData(new School());
        setEditing(false);
    }

   

     // для каждого типа сущности своя форма, 
    // которая передается дальше в абстрактный компонент Catalog в качестве props.form
    const form = <Form onSubmit={submitForm}>
                    <Form.Group className="mb-3" controlId="name">                     
                        <Form.Label>Название</Form.Label>
                        <Form.Control name="name" value={data.name} type="input" placeholder="Enter text" onChange={handleFormChange} required/>                   
                    </Form.Group>              
                    <Button variant="primary" type="submit">
                        Отправить
                    </Button>
                </Form>;
    

    const [showModalForm, setShowChoosing] = useState(false);
    const formChooseSchool = <Form onSubmit={redirectToSchool}>
                                <Form.Group className="mb-3" controlId="name">                     
                                <Form.Label>Автошкола</Form.Label>
                                    <Form.Select name="name_select" type="input" onChange={(e) => {setChosenSchool(e.target.value)}} required>
                                        <option selected disabled>Выберите автошколу</option>
                                    {
                                        items.map((school) => <option key={`School_${school.id}`} value={`${school.id}`}>{`${school.name}`}</option>)
                                    } 
                                    </Form.Select>                   
                                </Form.Group>                                        
                                <Button variant="primary" type="submit">
                                    Перейти
                                </Button>
                            </Form>;
    
    function showModalFormChoosing() {
        setShowChoosing(true);
    }
    function unshowModalFormChoosing() {
        setShowChoosing(false);
    }
    const [chosenSchool, setChosenSchool] = useState(0);
    const navigate = useNavigate();

    function redirectToSchool(item) {
        setSelectedId(item);
        navigate(`/school/${item}`);
    }

    return <div className="container-lg pt-5 min-vh-100">
        <ModalForm show={showModalForm} onClose={unshowModalFormChoosing} modalTitle={"Выбор автошколы"} form={formChooseSchool}></ModalForm>
        <Catalog name={nameCatalog}
                    headers={headers} 
                    items={items} 
                    onAdd={handleAdd}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    onChoose={redirectToSchool}
                    onClose={reset}
                    onBtnAdd={reset}
                    form={form}>
        </Catalog>
    </div>


}