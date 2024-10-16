import ItemTable from './ItemTable';
export default function Table(props) {
    function edit(itemId) {
        props.onEdit(itemId)
    }
    function remove(itemId) {
        props.onDelete(itemId);
    }
    function chooseSchool(itemId) {
        props.onChoose(itemId);
    }

    return <div >
            <table className={`table table-hover`}>
            <thead>
                    <tr>
                        {
                        props.headers.map((header) => <th key={header.name}>{header.label}</th>)
                        }
                        {props.isOnlyView || <th key='controls'>Элементы управления</th>}
                        
                    </tr>
            </thead>
            <tbody>
                    {
                    props.items.map((item, index) => 
                    <ItemTable 
                    key={index} 
                    headers={props.headers} 
                    item={item} 
                    onDelete={remove} 
                    onEdit={edit}
                    onChoose={chooseSchool}
                    isOnlyView={props.isOnlyView}/>)
                    }
            </tbody>
        </table>
        </div>
}