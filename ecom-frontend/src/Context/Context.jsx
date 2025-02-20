import axios from "../axios";
import { useState, useEffect, createContext, useCallback } from "react";

const AppContext = createContext({
  data: [],
  isError: "",
  userInfo: {},
  cart: [],
  isLoggedIn: false,
  addToCart: (product) => {},
  removeFromCart: (productId) => {},
  refreshData: () => {},
  updateStockQuantity: (productId, newQuantity) => {},
  updateLoggedIn: (val) => {},
  updateUserInfo: (userInfo) => {},
  setCartData: (cartData) => {},
});

export const AppProvider = ({ children }) => {
  const [data, setData] = useState([]);
  const [isError, setIsError] = useState("");
  const [userInfo, setUserInfo] = useState({});
  const [cart, setCart] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const addToCart = (product) => {
    const existingProductIndex = cart.findIndex(
      (item) => item.id === product.id
    );
    if (existingProductIndex !== -1) {
      let updatedProduct;
      const updatedCart = cart.map((item, index) => {
        if (index === existingProductIndex) {
          updatedProduct = { ...item, quantity: item.quantity + 1 };
          return updatedProduct;
        } else {
          return item;
        }
      });
      setCart(updatedCart);
      addorUpdateToCartCall({
        productId: updatedProduct.id,
        userId: userInfo.id,
        quantity: 1,
        price: updatedProduct.price,
        cartId: userInfo.cartId,
      });
    } else {
      const updatedProduct = { ...product, quantity: 1 };
      const updatedCart = [...cart, updatedProduct];
      console.log("updatedProducth", updatedProduct);
      setCart(updatedCart);
      addorUpdateToCartCall({
        productId: updatedProduct.id,
        userId: userInfo.id,
        quantity: 1,
        price: updatedProduct.price,
        cartId: userInfo.cartId,
      });
    }
  };

  const addorUpdateToCartCall = async (productData) => {
    console.log("productData:", productData);
    await axios.post("http://localhost:8080/api/cart", productData);
  };

  const updateCartCall = () => {};

  const removeFromCart = (cartItemId) => {
    const updatedCart = cart.filter((item) => item.id !== cartItemId);
    axios.delete(`http://localhost:8080/api/cart/${cartItemId}`);
    setCart(updatedCart);
  };

  const refreshData = useCallback(async (filterOption) => {
    console.log("here");
    try {
      const response = await axios.get("/products", {
        params: filterOption,
      });
      setData(response.data);
    } catch (error) {
      setIsError(error.message);
    }
  }, []);

  const clearCart = () => {
    setCart([]);
  };

  const updateLoggedIn = (val) => {
    setIsLoggedIn(val);
  };
  const updateUserInfo = (data) => {
    setUserInfo(data);
  };

  const setCartData = (cartData) => {
    setCart(cartData);
  };

  return (
    <AppContext.Provider
      value={{
        data,
        isError,
        cart,
        isLoggedIn,
        userInfo,
        addToCart,
        removeFromCart,
        refreshData,
        clearCart,
        updateLoggedIn,
        updateUserInfo,
        setCartData,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export default AppContext;
