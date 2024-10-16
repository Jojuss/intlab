import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import { React} from 'react';
export default function ModalForm(props) {  
    return <Modal show={props.show} onHide={props.onClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{props.modalTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>                  
                    {props.form}
                </Modal.Body>
            </Modal>
}