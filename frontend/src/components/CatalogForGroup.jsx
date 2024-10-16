import TableForGroup from "./commons/TableForGroup.jsx";
// это абстрактный компонент для всех справочников
export default function CatalogForGroup(props) {

    return  <>
        <div>{props.name}</div>
        <TableForGroup
            headers={props.headers}
            items={props.items}
        /></>

}