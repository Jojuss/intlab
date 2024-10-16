export default function ItemTableForGroup(props) {

    return <tr key={props.item.id}>
        {
            props.headers.map((header) => <td key={`${header.name}_${props.item.id}`}>{props.item[header.name]}</td>)
        }
    </tr>
}