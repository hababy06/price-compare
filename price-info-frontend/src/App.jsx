import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import AddPromotionPage from './pages/AddPromotionPage';

function App() {
  return (
    <Router>
      <div>
        <h1>我的價格管理系統</h1>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/add-promotion" element={<AddPromotionPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;