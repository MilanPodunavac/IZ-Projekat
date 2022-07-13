const MotherboardTable = ({ results }) => {

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
                            <th scope="col">Chipset</th>
                            <th scope="col">Format</th>
                            <th scope="col">DIMM slots</th>
                            <th scope="col">Maximum RAM</th>
                            <th scope="col">RAM type</th>
                            <th scope="col">Socket</th>
                            <th scope="col">PCIex16</th>
                            <th scope="col">PCIex8</th>
                            <th scope="col">PCIex4</th>
                            <th scope="col">PCIex1</th>
                            <th scope="col">SATA connections</th>
                            <th scope="col">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{result.name}</td>
                                <td>{result.manufacturer}</td>
                                <td>{result.chipset}</td>
                                <td>{result.format}</td>
                                <td>{result.dimmSlots}</td>
                                <td>{result.maxRam}</td>
                                <td>{result.ramType}</td>
                                <td>{result.socket}</td>
                                <td>{result.pciex16}</td>
                                <td>{result.pciex8}</td>
                                <td>{result.pciex4}</td>
                                <td>{result.pciex1}</td>
                                <td>{result.sataConnections}</td>
                                <td>{result.cost}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default MotherboardTable;