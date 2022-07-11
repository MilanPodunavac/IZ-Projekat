import { v4 as uuidv4 } from 'uuid';

const Navbar = () => {

    return (
        <div className="m-5 p-1">
            <ul className="nav nav-pills nav-fill ">
                <li className="nav-item" key={uuidv4()}>
                    <a style={{ textDecoration: "none" }} href="/" ><h1>System for support</h1></a>
                </li>
                <li className="nav-item" key={uuidv4()}>
                    <a className="nav-link" href="/recommendation">Component recommendation</a>
                </li>
                <li className="nav-item" key={uuidv4()}>
                    <a className="nav-link" href="/estimate">Compatibility estimate</a>
                </li>
                <li className="nav-item" key={uuidv4()}>
                    <a className="nav-link" href="/failure">Cause of failure</a>
                </li>
                <li className="nav-item" key={uuidv4()}>
                    <a className="nav-link" href="/similarity">Similar PCs</a>
                </li>
            </ul>
            <hr></hr>
        </div>
    );
}

export default Navbar;