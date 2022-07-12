const GpuTable = ({ results }) => {

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
                            <th scope="col">Memory type</th>
                            <th scope="col">Memory size</th>
                            <th scope="col">Memory bus</th>
                            <th scope="col">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{result.name}</td>
                                <td>{result.manufacturer}</td>
                                <td>{result.memoryType}</td>
                                <td>{result.memorySize}</td>
                                <td>{result.memoryBus}</td>
                                <td>{result.price}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default GpuTable;