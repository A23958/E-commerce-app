import { useState } from "react";
import API from "../axios";

export default function SignUp({ onBack, onRegistered }) {
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");

    const fields = new FormData(event.currentTarget);
    const username = fields.get("username").trim();
    const password = fields.get("password");

    if (!username) {
      setError("Enter a username.");
      return;
    }

    if (password !== fields.get("confirmPassword")) {
      setError("Passwords do not match.");
      return;
    }

    setSubmitting(true);

    try {
      const { data: csrf } = await API.get("/auth/csrf");

      await API.post("/auth/register", { username, password }, {
        headers: { [csrf.headerName]: csrf.token },
      });

      onRegistered();
    } catch (error) {
      setError(
        error.response?.status === 409
          ? "That username is already taken."
          : "Could not create your account. Please check your details and try again."
      );
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="login-page">
      <form className="login-form" onSubmit={handleSubmit}>
        <h1>Create account</h1>

        <label>
          Username
          <input name="username" autoComplete="username" required />
        </label>

        <label>
          Password
          <input name="password" type="password" autoComplete="new-password" required />
        </label>

        <label>
          Confirm password
          <input name="confirmPassword" type="password" autoComplete="new-password" required />
        </label>

        <button type="submit" disabled={submitting}>
          {submitting ? "Creating account…" : "Create account"}
        </button>

        {error && <p role="alert">{error}</p>}

        <button type="button" onClick={onBack} disabled={submitting}>
          Back to sign in
        </button>
      </form>
    </main>
  );
}