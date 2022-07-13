const SimilarityTable = ({ results }) => {

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
                                <td>{result.motherboard.name}</td>
                                <td>{result.cpu.name}</td>
                                <td>{result.gpu.name}</td>
                                <td>{result.ram.name}</td>
                                {/*<td>{result.psu.name}</td>*/}
                                <td>{result.disk.name}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default SimilarityTable;