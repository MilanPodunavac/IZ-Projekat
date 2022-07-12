import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useEffect } from 'react';
import { v4 as uuidv4 } from 'uuid';
import Navbar from './components/Navbar';
import axios from 'axios';
import CauseOfFailure from './components/CauseOfFailure';
import ComponentRecommendation from './components/ComponentRecommendation';
import ChooseComponents from './components/ChooseComponents';

axios.defaults.baseURL = 'http://localhost:8080/';

function App() {

  useEffect(() => {
    document.title = "Support"
  }, [])

  return (
    <div className="App">
      <Router>
      <Routes>
          <Route key={uuidv4()} exact path="/" element={[<Navbar key={uuidv4()}/>]}/>
          <Route key={uuidv4()} exact path="/recommendation" element={[<Navbar key={uuidv4()}/>, <ComponentRecommendation key={uuidv4()}/>]}/>
          <Route key={uuidv4()} exact path="/estimate" element={[<Navbar key={uuidv4()}/>, <ChooseComponents key={uuidv4()} useCase={'compatibility'} />]}/>
          <Route key={uuidv4()} exact path="/failure" element={[<Navbar key={uuidv4()}/>, <CauseOfFailure key={uuidv4()}/>]}/>
          <Route key={uuidv4()} exact path="/similarity" element={[<Navbar key={uuidv4()}/>, <ChooseComponents key={uuidv4()} useCase={'similarity'} />]}/>
          </Routes>
        </Router>
    </div>
  );
}

export default App;
