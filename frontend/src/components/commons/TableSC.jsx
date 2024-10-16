import ItemTableSC from './ItemTableSC';
export default function TableSC(props) {
    function edit(itemId) {
        props.onEdit(itemId)
    }
    function remove(itemId) {
        props.onDelete(itemId);
    }

    return <div >
        <table className={`table table-hover`}>
            <thead>
            <tr>
                {
                    props.headers.map((header) => <th key={header.name}>{header.label}</th>)
                }
                {localStorage.getItem("role") !== "ADMIN" || props.isOnlyView || <th key='controls'>Элементы управления</th>}

            </tr>
            </thead>
            <tbody>
            {
                props.items.map((item, index) =>
                    <ItemTableSC
                        key={index}
                        headers={props.headers}
                        item={item}
                        onDelete={remove}
                        onEdit={edit}
                        isOnlyView={props.isOnlyView}/>)
            }
            </tbody>
        </table>
    </div>
}