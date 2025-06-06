import axios from "axios";

export const getAllPriceInfos = () => axios.get("/api/price-infos");