import Button from 'react-bootstrap/Button';
import TableSC from "./commons/TableSC.jsx";
import { useState} from 'react';
import ModalForm from './commons/ModalForm.jsx';
// это абстрактный компонент для всех справочников
export default function CatalogSC(props) {
    const [show, setShow] = useState(false);
    const [modalTitle, setModalTitle] = useState("");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    function handleAdd() {
        setModalTitle("Добавление");
        props.onBtnAdd();
        handleShow();
    }
    function handleEdit(itemId) {
        setModalTitle("Редактирование");
        props.onEdit(itemId);
        handleShow();
    }
    function handleRemove(item) {
        props.onDelete(item);
    }
    function changeData(event) {
        props.onFormChanged(event);
    }

    return  <>
        <div>{props.name}</div>
        <Button variant="success" onClick={handleAdd}>Добавить</Button>
        <TableSC
            headers={props.headers}
            items={props.items}
            onEdit={handleEdit}
            onDelete={handleRemove}
        />
        <ModalForm
            show={show}
            onClose={handleClose}
            modalTitle={modalTitle}
            // onSubmit={submitForm}
            onChange={changeData}
            form={props.form}
        /></>

}