import Button from 'react-bootstrap/Button';
export default function ItemTableSC(props) {
    function edit() {
        props.onEdit(props.item.id);
    }
    function remove() {
        props.onDelete(props.item.id);
    }

    return <tr key={props.item.id}>
        {
            props.headers.map((header) => <td key={`${header.name}_${props.item.id}`}>{props.item[header.name]}</td>)
        }
        {localStorage.getItem("role") !== "ADMIN" ||props.isOnlyView || <td key={`controls_${props.item.id}`}>
            {localStorage.getItem("role") === "ADMIN" && <Button variant="btn btn-outline-primary" onClick={edit}>Редактировать</Button>}
            {localStorage.getItem("role") === "ADMIN" && <Button variant="btn btn-outline-danger" onClick={remove}>Удалить</Button>}</td>}
    </tr>
}