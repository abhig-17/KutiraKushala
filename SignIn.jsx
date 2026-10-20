import React, { useState } from 'react';
import { ChevronLeft } from 'lucide-react';

export default function SignIn({ onBack, onSubmitSuccess, onForgotPassword }) {
  const [activeRole, setActiveRole] = useState('seller'); 
  const [mobileNumber, setMobileNumber] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (onSubmitSuccess) {
      onSubmitSuccess({ activeRole, mobileNumber, password });
    } else {
      alert(`Signed in successfully as ${activeRole}!`);
    }
  };

  return (
    <div className="bg-[#F9F8F4] pt-16 px-6 min-h-screen flex flex-col pb-12">
      {/* Header Row */}
      <div className="flex items-center gap-3">
        <button
          onClick={onBack}
          className="p-1 -ml-1 text-[#1A2E26] bg-transparent border-none cursor-pointer flex items-center justify-center"
          aria-label="Back"
        >
          <ChevronLeft size={24} />
        </button>
        <h1 className="text-[#1A2E26] text-xl font-bold">
          Sign In
        </h1>
      </div>

      {/* Subtitle */}
      <p className="text-slate-500 mt-4 mb-6 text-sm">
        Welcome back! Please enter your details.
      </p>

      {/* Role Toggle Segmented Control */}
      <div className="bg-slate-100 rounded-xl p-1 mb-6 flex">
        <button
          type="button"
          onClick={() => setActiveRole('seller')}
          className={`${
            activeRole === 'seller'
              ? 'bg-white shadow-sm text-[#1A2E26] font-semibold rounded-lg'
              : 'bg-transparent text-slate-500 hover:text-slate-700 font-medium'
          } flex-1 py-2 text-center text-sm transition-all border-none cursor-pointer`}
        >
          Seller
        </button>
        <button
          type="button"
          onClick={() => setActiveRole('buyer')}
          className={`${
            activeRole === 'buyer'
              ? 'bg-white shadow-sm text-[#1A2E26] font-semibold rounded-lg'
              : 'bg-transparent text-slate-500 hover:text-slate-700 font-medium'
          } flex-1 py-2 text-center text-sm transition-all border-none cursor-pointer`}
        >
          Buyer
        </button>
      </div>

      
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        {/* Mobile Number */}
        <div>
          <label className="block text-[#1A2E26] text-sm font-semibold mb-1.5">
            Mobile Number
          </label>
          <input
            type="tel"
            value={mobileNumber}
            onChange={(e) => setMobileNumber(e.target.value)}
            placeholder="+91 98765 43210"
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
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="........"
            required
            className="w-full bg-white border border-gray-200 rounded-xl px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-[#2A5A4A]/20"
          />
          {/* Forgot Password Link */}
          <button
            type="button"
            onClick={onForgotPassword || (() => alert('Forgot Password link clicked'))}
            className="text-[#2A5A4A] text-sm font-semibold text-right w-full mt-2 bg-transparent border-none cursor-pointer block"
          >
            Forgot Password?
          </button>
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className="w-full bg-[#2A5A4A] text-white font-semibold py-3.5 rounded-xl mt-6 hover:bg-[#234b3e] transition-colors cursor-pointer border-none"
        >
          Sign In
        </button>
      </form>
    </div>
  );
}
