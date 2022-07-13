import { useNavigate } from 'react-router-dom';

const RamTable = ({ results }) => {
    const history = useNavigate();

    function lookComponent(name){
        localStorage.setItem('name',name);
        localStorage.setItem('type','Ram');
        history('/component');
    }

    return (
        <>
        {
                results &&
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Maximum frequency</th>
                            <th scope="col">Module capacity</th>
                            <th scope="col">Module number</th>
                            <th scope="col">Type</th>
                            <th scope="col">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td><a href="#" onClick={() => lookComponent(result.name)}>{result.name}</a></td>
                                <td>{result.manufacturer}</td>
                                <td>{result.frequency}</td>
                                <td>{result.capacity}</td>
                                <td>{result.numberOfModules}</td>
                                <td>{result.type}</td>
                                <td>{result.price}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default RamTable;