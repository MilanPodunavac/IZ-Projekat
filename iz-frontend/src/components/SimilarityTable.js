const SimilarityTable = ({ results }) => {

    return (
        <>
        {
                results &&
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Motherboard</th>
                            <th scope="col">CPU</th>
                            <th scope="col">GPU</th>
                            <th scope="col">RAM</th>
                            <th scope="col">Power supply unit</th>
                            <th scope="col">Storage</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{result.motherboard}</td>
                                <td>{result.cpu}</td>
                                <td>{result.gpu}</td>
                                <td>{result.ram}</td>
                                <td>{result.psu}</td>
                                <td>{result.storage}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default SimilarityTable;