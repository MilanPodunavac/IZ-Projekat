const CompatibilityTable = ({ results }) => {

    function getPercentageFromProbability(data) {
        return data * 100;
    }

    return (
        <>
            {
                results &&
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Usage</th>
                            <th scope="col">Compatibility</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{result.usage}</td>
                                <td>{getPercentageFromProbability(result.compatibility)} %</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default CompatibilityTable;