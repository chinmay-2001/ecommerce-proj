import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AppContext from "../Context/Context";
import API from "../axios";

function UpdateProduct() {
  const initialProductState = {
    id: "",
    name: "",
    brand: "",
    desc: "",
    price: "",
    category: "",
    quantity: "",
    release_date: "",
    available: false,
    imageData: "",
    imageName: "",
    imageType: "",
  };

  const [product, setProduct] = useState(initialProductState);

  const [image, setImage] = useState(null);
  const [imageFile, setImageFile] = useState(null);

  const { data } = useContext(AppContext);

  const { id } = useParams();

  const getImageFile = (imageData, imageType) => {
    const byteCharacters = atob(imageData);
    const byteNumbers = new Array(byteCharacters.length)
      .fill(null)
      .map((_, i) => byteCharacters.charCodeAt(i));
    const byteArray = new Uint8Array(byteNumbers);

    const blob = new Blob([byteArray], {
      type: imageType,
    });

    return blob;
  };

  const convertToISO = (dateString) => {
    if (!dateString) return "";
    const parts = dateString.split("-");
    if (parts.length !== 3) return "";
    return `${parts[2]}-${parts[1]}-${parts[0]}`;
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleImageChange = (e) => {
    console.log("target:", e.target.files[0]);
    const imageFile = e.target.files[0];
    setImageFile(imageFile);
  };

  const createImageUrl = (imageFile) => {
    return URL.createObjectURL(imageFile);
  };

  const submitHandler = (e) => {
    e.preventDefault();
    const formData = new FormData();
    if (imageFile) {
      formData.append("imageFile", imageFile);
    } else {
      const byteArray = new Uint8Array(product.imageData); // Convert byte array to Uint8Array
      const blob = new Blob([byteArray], { type: product.imageType });
      console.log(blob);
      formData.append("imageFile", blob);
    }

    formData.append(
      "product",
      new Blob([JSON.stringify(product)], { type: "application/json" })
    );

    for (let [key, value] of formData.entries()) {
      console.log(key, value);
    }
    axios
      .put("http://localhost:8080/api/product", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log("Product added successfully:", response.data);
        alert("Product added successfully");
      })
      .catch((error) => {
        console.error("Error adding product:", error);
        alert("Error adding product");
      });
  };

  useEffect(() => {
    if (!data.length) return;

    const foundProduct = data.find((p) => p.id === Number(id));
    if (foundProduct || initialProductState) {
      setProduct(foundProduct);
      setImage(
        createImageUrl(
          getImageFile(foundProduct.imageData, foundProduct.imageType)
        )
      );
    }
  }, [id, data]);

  return (
    <div className="container">
      <div className="center-container">
        <form className="row g-3 pt-5" onSubmit={submitHandler}>
          <div className="col-md-6">
            <label className="form-label">
              <h6>Name</h6>
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Product Name"
              onChange={handleInputChange}
              value={product.name}
              name="name"
            />
          </div>

          <div className="col-md-6">
            <label className="form-label">
              <h6>Brand</h6>
            </label>
            <input
              type="text"
              name="brand"
              className="form-control"
              placeholder="Enter your Brand"
              value={product.brand}
              onChange={handleInputChange}
              id="brand"
            />
          </div>

          <div className="col-12">
            <label className="form-label">
              <h6>Description</h6>
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Add product description"
              value={product.desc}
              name="desc"
              onChange={handleInputChange}
              id="desc"
            />
          </div>

          <div className="col-5">
            <label className="form-label">
              <h6>Price</h6>
            </label>
            <input
              type="number"
              className="form-control"
              placeholder="Eg: $1000"
              onChange={handleInputChange}
              value={product.price}
              name="price"
              id="price"
            />
          </div>

          <div className="col-md-6">
            <label className="form-label">
              <h6>Category</h6>
            </label>
            <select
              className="form-select"
              value={product.category}
              onChange={handleInputChange}
              name="category"
              id="category"
            >
              <option value="">Select category</option>
              <option value="Laptop">Laptop</option>
              <option value="Headphone">Headphone</option>
              <option value="Mobile">Mobile</option>
              <option value="Electronics">Electronics</option>
              <option value="Toys">Toys</option>
              <option value="Fashion">Fashion</option>
            </select>
          </div>

          <div className="col-md-4">
            <label className="form-label">
              <h6>Stock Quantity</h6>
            </label>
            <input
              type="number"
              className="form-control"
              placeholder="Stock Remaining"
              onChange={handleInputChange}
              value={product.quantity}
              name="quantity"
              // value={`${stockAlert}/${stockQuantity}`}
              id="quantity"
            />
          </div>

          <div className="col-md-4">
            <label className="form-label">
              <h6>Release Date</h6>
            </label>
            <input
              type="date"
              className="form-control"
              value={convertToISO(product.release_date)}
              name="release_date"
              onChange={handleInputChange}
              id="release_date"
            />
          </div>

          {imageFile ? (
            <img className="resize-mg" src={createImageUrl(imageFile)} />
          ) : (
            product.imageData && <img className="resize-mg" src={image} />
          )}

          <div className="col-md-4">
            <label className="form-label">
              <h6>Image</h6>
            </label>
            <input
              className="form-control"
              type="file"
              onChange={handleImageChange}
            />
          </div>

          <div className="col-12">
            <div className="form-check">
              <input
                className="form-check-input"
                type="checkbox"
                name="available"
                id="gridCheck"
                checked={product.available}
                onChange={handleImageChange}
              />
              <label className="form-check-label">Product Available</label>
            </div>
          </div>

          <div className="col-12">
            <button
              type="submit"
              className="btn btn-primary"
              onClick={submitHandler}
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default UpdateProduct;
