import { useEffect, useState } from 'react';
import axios from 'axios';
import Multiselect from 'multiselect-react-dropdown';

const ComponentSelectBox = ({ selectedValue, setSelectedValue, path, message, className, propertyName ,singleSelect}) => {
    const [objects, setObjects] = useState([]);

    useEffect(() => {
        axios.get(axios.defaults.baseURL + path).then(res => {
            setJson(res.data)
        }).catch(err => {
            console.log(err);
        })
    },[])

    const setJson = (data) => {
        let json = []

        if (data[propertyName] || data.length>=0) {
            data[propertyName].forEach((dataMember) => {
                
                json = json.concat({
                    id: dataMember.name,
                    content: dataMember.name
                })
            })
            
            setObjects(json);
        }
    }

    const changeHandle = (e) => {
        setSelectedValue(Array.isArray(e)?e.map(x=>x.id):[]);
    }

    return (
        <Multiselect
            className={className}
            //id="id"
            //name="select"
            options={objects}
            displayValue="content"
            onSelect={(e) => changeHandle(e)}
            onRemove={(e) => changeHandle(e)}
            //singleSelect={singleSelect}
            selectionLimit={1}
        >
        </Multiselect>
    );
}

export default ComponentSelectBox;