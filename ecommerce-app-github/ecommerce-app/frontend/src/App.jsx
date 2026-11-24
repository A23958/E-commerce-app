import "./App.css";
import React, { useState, useEffect } from "react";
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import Cart from "./components/Cart";
import AddProduct from "./components/AddProduct";
import Product from "./components/Product";
import SignUp from "./components/SignUp";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AppProvider } from "./Context/Context";
import UpdateProduct from "./components/UpdateProduct";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import 'bootstrap/dist/css/bootstrap.min.css';
import API from "./axios";



function StoreApp() {
  const [cart, setCart] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
    console.log("Selected category:", category);
  };
  const addToCart = (product) => {
    const existingProduct = cart.find((item) => item.id === product.id);
    if (existingProduct) {
      setCart(
        cart.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      );
    } else {
      setCart([...cart, { ...product, quantity: 1 }]);
    }
  };

  return (
    <AppProvider>
      <BrowserRouter>
        <Navbar onSelectCategory={handleCategorySelect}
         />
        <Routes>
          <Route
            path="/"
            element={
              <Home addToCart={addToCart} selectedCategory={selectedCategory}
              />
            }
          />
          <Route path="/add_product" element={<AddProduct />} />
          <Route path="/product" element={<Product  />} />
          <Route path="product/:id" element={<Product  />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/product/update/:id" element={<UpdateProduct />} />
        </Routes>
      </BrowserRouter>
    </AppProvider>
  );
}

//export default App;
export default function App() {
  const [session, setSession] = useState("checking");
  const [message, setMessage] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [showSignUp, setShowSignUp] = useState(false);

  useEffect(() => {
    API.get("/auth/me")
      .then((response) => {
        setSession(response.status === 204 ? "signed-in" : "error");
      })
      .catch((error) => {
        setSession(error.response?.status === 401 ? "signed-out" : "error");
      });
  }, []);

  async function handleLogin(event) {
    event.preventDefault();
    const fields = new FormData(event.currentTarget);
    const credentials = new URLSearchParams({
      username: fields.get("username"),
      password: fields.get("password"),
    });

    setSubmitting(true);
    setMessage("");

    try {
      const { data: csrf } = await API.get("/auth/csrf");
      const response = await API.post("/auth/login", credentials, {
        headers: { [csrf.headerName]: csrf.token },
      });

      if (response.status === 204) {
        setSession("signed-in");
      } else {
        setMessage("Unexpected response from the login endpoint.");
      }
    } catch (error) {
      setMessage(
        error.response?.status === 401
          ? "Incorrect username or password."
          : "Could not sign in. Check DevTools → Network."
      );
    } finally {
      setSubmitting(false);
    }
  }

  if (session === "checking") return <p>Checking session…</p>;
  if (session === "error") return <p>Could not check your session. Reload to retry.</p>;
  if (session === "signed-in") return <StoreApp />;
  if (showSignUp) {
  return (
    <SignUp
      onBack={() => setShowSignUp(false)}
      onRegistered={() => {
        setMessage("Account created. Please sign in.");
        setShowSignUp(false);
      }}
    />
  );
  }  

  return (
    <main className="login-page">
      <form className="login-form" onSubmit={handleLogin}>
        <h1>Sign in</h1>

        <label>
          Username
          <input name="username" autoComplete="username" required />
        </label>

        <label>
          Password
          <input
            name="password"
            type="password"
            autoComplete="current-password"
            required
          />
        </label>

        <button type="submit" disabled={submitting}>
          {submitting ? "Signing in…" : "Sign in"}
        </button>

        <button
        type="button"
        onClick={() => {
          setMessage("");
          setShowSignUp(true);
        }}

        >
          Create an account
        </button>


        {message && <p role="alert">{message}</p>}
      </form>
    </main>
  );
}
