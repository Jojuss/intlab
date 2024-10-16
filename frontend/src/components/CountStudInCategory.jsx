import { useState, useEffect } from 'react';
import DataService from '../services/DataService';
import CatalogForGroup from "./CatalogForGroup.jsx";
import { useNavigate } from "react-router-dom";
import GroupedStudAndCategoryDto from "../models/GroupedStudAndCategoryDto";

export default function CountStudInCategory(props) {

  const headers = [
    {name: 'categoryName', label: "Название"},
    {name: 'studentsCount', label: "Студенты"},
  ];

  const nameCatalog = "Количество студентов в элективе";

  const url = '/categoryStudent/groupbycategory';

  const [items, setItems] = useState([]);

  useEffect(() => {
    loadItems();
  }, []);

  function loadItems() {
    DataService.readAll(url, (data) => new GroupedStudAndCategoryDto(data))
        .then(data => setItems(data));
  }

  return <div className="container-lg pt-5 min-vh-100">
    <CatalogForGroup name={nameCatalog}
             headers={headers}
             items={items}>
    </CatalogForGroup>
  </div>


}