import "../form.css";
import React, { useState } from "react";
import axios from "axios";


const RegistrationForm = () => {
    const [formData, setFormData] = useState({
        email: "",
        firstName: "",
        lastName: ""
    });

    //handle real time input
    const realTimeData = (e) => {
        setFormData({
            //It spreads the current formData object, ensuring that all existing key-value pairs remain unchanged.
            ...formData,
            //Then, it overwrites the field that is currently being updated (e.target.name
            [e.target.name]: e.target.value
        });
    }

    const [errors, setErrors] = useState({}) //keeps track of errors in current state
    //validate inputs
    const validate = () => {
        let newErrors = {};
        if (!formData.name) newErrors.name = "Name is required.";
        if (!formData.email) newErrors.email = "Email is required.";
        if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = "Email is invalid.";
        if (!formData.password) newErrors.password = "Password is required.";
        if (formData.password.length < 6) newErrors.password = "Password must be at least 6 characters.";
        if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = "Passwords do not match.";

        setErrors(newErrors); //updates the errors state so that the UI can use it to display error messages
        return Object.keys(newErrors) === 0; //returns an array of the object's keys (newErrors) if the length of the array is 0 then true
    }

    //submissionHandler
    const handleSubmit = async (e) => { //async function to be able to use await, to wait for connecting to server, uses e for event object
        e.preventDefault(); //stops the page from refreshing, allowing you to handle form submission using JavaScript
        if (validate()) {
            //axios http request
            try {
                await axios.post("http://localhost:8080/user/register", formData, {
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                alert("Registration Successful!");
            } catch (error) {
                console.log("Error:", error);
                alert("Registration Failed!");
            }
        }
    }

    return (
        <div className="form-container">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input type="text" name="name" value={formData.email} onChange={realTimeData} />
                    {/* curly braces {} are used to embed JavaScript expressions inside JSX, dynamically displays an error message only when errors.name exists */}
                    {errors.name && <p className="error">{errors.name}</p>}
                </div>

                <div>
                    <label>Email:</label>
                    <input type="email" name="email" value={formData.email} onChange={realTimeData} />
                    {/* curly braces {} are used to embed JavaScript expressions inside JSX, dynamically displays an error message only when errors.name exists */}
                    {errors.email && <p className="error">{errors.email}</p>}
                </div>
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default RegistrationForm;