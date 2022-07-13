import { useState, useEffect } from "react";
import axios from "axios";

const ShowSingleComponent = () => {
    const [keys, setKeys] = useState();
    const [results, setResults] = useState()

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

    useEffect(() => {
        if (localStorage.getItem('type') !== 'MemoryDisc') {
            axios.get(axios.defaults.baseURL + 'api/' + localStorage.getItem('type') + '/' + localStorage.getItem('name')).then(res => {
                setResults(res.data);
                setKeys(Object.keys(res.data));
            }).catch(err => {
                console.log(err);
            })
        }else{
            axios.post(axios.defaults.baseURL + 'api/' + localStorage.getItem('type') + '/getByName',{
                'name' : localStorage.getItem('name')
            }).then(res => {
                setResults(res.data);
                setKeys(Object.keys(res.data));
            }).catch(err => {
                console.log(err);
            })
        }
    }, [])

    return (
        <div style={{ maxWidth: "50%", margin: "auto" }}>
            {localStorage.getItem('name').length == 0 && <h1>Nothing to display!</h1>}
            {
                results &&
                <h1>{localStorage.getItem('name')}</h1>
            }
            {
                results &&
                <table className="table">
                    <tbody>
                        {results && (keys.map((key, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <th>{getName(key)}</th>
                                <td>{results[key]}</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </div>
    );
}

export default ShowSingleComponent;