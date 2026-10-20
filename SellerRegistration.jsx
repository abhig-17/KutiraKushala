import React, { useState } from 'react';
import { ChevronLeft, ChevronDown } from 'lucide-react';

export default function SellerRegistration({ onBack, onSubmitSuccess }) {
  const [formData, setFormData] = useState({
    fullName: '',
    businessName: '',
    mobileNumber: '',
    category: '',
    location: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (onSubmitSuccess) {
      onSubmitSuccess(formData);
    } else {
      alert('Account created successfully!');
    }
  };

  return (
    <div className="bg-[#F9F8F4] pt-16 px-6 min-h-screen flex flex-col pb-12">
  
      <div className="flex items-center gap-3">
        <button
          onClick={onBack}
          className="p-1 -ml-1 text-[#1A2E26] bg-transparent border-none cursor-pointer flex items-center justify-center"
          aria-label="Back"
        >
          <ChevronLeft size={24} />
        </button>
        <h1 className="text-[#1A2E26] text-xl font-bold">
          Seller Registration
        </h1>
      </div>

      {/* Subtitle */}
      <p className="text-slate-500 mt-4 mb-6 text-sm leading-relaxed">
        Join as a local maker and reach wholesale buyers directly.
      </p>

      {/* Form Elements */}
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        {/* Full Name */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Full Name
          </label>
          <input
            type="text"
            name="fullName"
            value={formData.fullName}
            onChange={handleChange}
            placeholder="e.g. Ramesh Kumar"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
        </div>

        {/* Business / Workshop Name */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Business / Workshop Name
          </label>
          <input
            type="text"
            name="businessName"
            value={formData.businessName}
            onChange={handleChange}
            placeholder="e.g. Ramesh Bamboo Crafts"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
        </div>

        {/* Mobile Number */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Mobile Number
          </label>
          <input
            type="tel"
            name="mobileNumber"
            value={formData.mobileNumber}
            onChange={handleChange}
            placeholder="+91 98765 43210"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
        </div>

        {/* Product Category Dropdown */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Product Category
          </label>
          <div className="relative">
            <select
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
              className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm appearance-none focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20 text-slate-700"
            >
              <option value="" disabled>Select primary category</option>
              <option value="Crafts">Crafts & Handicrafts</option>
              <option value="Food">Food & Processed Items</option>
              <option value="Textiles">Textiles & Handloom</option>
              <option value="Handmade">Handmade Decor</option>
              <option value="Agarbatti">Agarbatti & Spices</option>
            </select>
            <ChevronDown size={18} className="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400 pointer-events-none" />
          </div>
        </div>

        {/* Business Location */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Business Location
          </label>
          <input
            type="text"
            name="location"
            value={formData.location}
            onChange={handleChange}
            placeholder="e.g. Mysuru, Karnataka"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
        </div>

        {/* Password */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Password
          </label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="........"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className="w-full bg-[#2A5A4A] text-white font-semibold py-3.5 rounded-xl mt-4 hover:bg-[#234b3e] transition-colors cursor-pointer border-none"
        >
          Create Account
        </button>
      </form>

      {/* Disclaimer Footer */}
      <p className="text-xs text-slate-500 text-center mt-4 leading-normal">
        By creating an account, you agree to our{' '}
        <span className="text-[#2A5A4A] font-semibold cursor-pointer">Terms of Service</span>
        {' '}and{' '}
        <span className="text-[#2A5A4A] font-semibold cursor-pointer">Privacy Policy</span>.
      </p>
    </div>
  );
}
