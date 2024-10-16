import ItemTableForGroup from './ItemTableForGroup';

export default function TableForGroup(props) {

    return <div >
        <table className={`table table-hover`}>
            <thead>
            <tr>
                {
                    props.headers.map((header) => <th key={header.name}>{header.label}</th>)
                }
            </tr>
            </thead>
            <tbody>
            {
                props.items.map((item, index) =>
                    <ItemTableForGroup
                        key={index}
                        headers={props.headers}
                        item={item}
                        isOnlyView={props.isOnlyView}/>)
            }
            </tbody>
        </table>
    </div>
}