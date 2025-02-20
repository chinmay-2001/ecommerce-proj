import "./App.css";
import { useState, useContext } from "react";
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import Cart from "./components/Cart.jsx";
import AddProduct from "./components/AddProduct";
import Product from "./components/Product";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AppProvider } from "./Context/Context";
import { Navigate, Outlet } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "bootstrap/dist/css/bootstrap.min.css";
import UpdateProduct from "./components/UpdateProduct.jsx";
import Login from "./components/Login.jsx";
import AppContext from "./Context/Context";
import { Signup } from "./components/SignUp.jsx";

function App() {
  const [cart, setCart] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedBrand, setSelectedBrand] = useState("");
  const [filterData, setFilterData] = useState({
    categories: [],
    brands: [],
  });

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
  };

  const handleBrandSelect = (brand) => {
    setSelectedBrand(brand);
  };

  const ProtectedRoutes = () => {
    const { isLoggedIn } = useContext(AppContext);
    return isLoggedIn ? <Outlet /> : <Navigate to="/login" replace />;
  };

  return (
    <AppProvider>
      <BrowserRouter>
        <Navbar
          onSelectCategory={handleCategorySelect}
          onSelectBrand={handleBrandSelect}
          filterData={filterData}
        />
        <Routes>
          <Route path="/Login" element={<Login />} />
          <Route path="/SignUp" element={<Signup />} />

          <Route element={<ProtectedRoutes />}>
            <Route
              path="/"
              element={
                <Home
                  selectedCategory={selectedCategory}
                  selectedBrand={selectedBrand}
                  setFilterData={setFilterData}
                />
              }
            />
            <Route path="/add_product" element={<AddProduct />} />
            <Route path="/product" element={<Product />} />
            <Route path="product/:id" element={<Product />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/product/update/:id" element={<UpdateProduct />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AppProvider>
  );
}

export default App;
