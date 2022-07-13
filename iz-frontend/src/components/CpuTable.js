import { useNavigate } from 'react-router-dom';

const CpuTable = ({ results }) => {
    const history = useNavigate();

    function lookComponent(name){
        localStorage.setItem('name',name);
        localStorage.setItem('type','Cpu');
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
                            <th scope="col">Frequency</th>
                            <th scope="col">Cores</th>
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
                                <td>{result.cores}</td>
                                <td>{result.price}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default CpuTable;