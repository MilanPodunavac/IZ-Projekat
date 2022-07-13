import { useNavigate } from 'react-router-dom';

const SimilarityTable = ({ results }) => {

    const history = useNavigate();

    function lookComponent(name,type){
        localStorage.setItem('name',name);
        localStorage.setItem('type',type);
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
                            <th scope="col">Motherboard</th>
                            <th scope="col">CPU</th>
                            <th scope="col">GPU</th>
                            <th scope="col">RAM</th>
                            {/*<th scope="col">Power supply unit</th>*/}
                            <th scope="col">Storage</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td><a href="#" onClick={() => lookComponent(result.motherboard.name,'Motherboard')}>{result.motherboard.name}</a></td>
                                <td><a href="#" onClick={() => lookComponent(result.cpu.name,'Cpu')}>{result.cpu.name}</a></td>
                                <td><a href="#" onClick={() => lookComponent(result.gpu.name, 'Gpu')}>{result.gpu.name}</a></td>
                                <td><a href="#" onClick={() => lookComponent(result.ram.name, 'Ram')}>{result.ram.name}</a></td>
                                {/*<td>{result.psu.name}</td>*/}
                                <td><a href="#" onClick={() => lookComponent(result.disk.name, 'MemoryDisc')}>{result.disk.name}</a></td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default SimilarityTable;