import { useState } from "react";

const CompatibilityTable = ({ results }) => {
    const [keys] = useState(Object.keys(results));

    function getName(name) {
        let wordList = name.split(/(?=[A-Z])/);

        let returnString = ''
        wordList.forEach((word) => {
            returnString += capitalizeFirstLetter(word) + ' ';
        })
        return returnString;
    }

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    return (
        <>
            {
                results &&
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Usage</th>
                            <th scope="col">Grade</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (keys.map((key, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{getName(key)}</td>
                                <td>{results[key]} %</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </>
    );
}

export default CompatibilityTable;