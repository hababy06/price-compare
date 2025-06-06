import PriceInfoList from './components/PriceInfoList';
import CsvUpload from './components/CsvUpload.jsx';

function App() {
  return (
    <div>
      <h1>我的價格管理系統</h1>
      <PriceInfoList />
      <h1>商品價格 CSV 匯入</h1>
      <CsvUpload />
    </div>
  );
}

export default App;