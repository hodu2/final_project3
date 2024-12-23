import React from "react";

export default function Input({ label, type, placeholder, name, ...props }) {
  return (
    <div>
       <label>
      {label}
      <input
        type={type}
        placeholder={placeholder} 
        name={name}
        id={name}
        required
        {...props}
      />
    </label>
    </div>
  );
};