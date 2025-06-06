import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import CsvUploadPage from './components/CsvUploadPage';
import PriceListPage from './components/PriceListPage';
import SearchPage from './components/SearchPage';
import AddPricePage from './components/AddPricePage'; // 修正導入路徑

function App() {
  return (
    <Router>
      <div>
        <h1>我的價格管理系統</h1>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/price-list" element={<PriceListPage />} />
          <Route path="/csv-upload" element={<CsvUploadPage />} />
          <Route path="/search" element={<SearchPage />} />
          <Route path="/add-price" element={<AddPricePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;