import React from 'react';
import { User, Building2, ArrowRight } from 'lucide-react';

export default function RoleSelection({ onSelectSeller, onSelectBuyer, onSignIn }) {
  return (
    <div className="bg-[#F9F8F4] pt-16 px-6 h-screen flex flex-col justify-between">
      <div>
        {/* Header Section */}
        <header>
          <h1 className="text-[#1A2E26] text-3xl font-bold">
            Welcome to
          </h1>
          <h2 className="text-[#2A5A4A] text-3xl font-bold">
            Kutira-Kushala
          </h2>
          <p className="text-slate-500 mt-4 text-base leading-relaxed">
            Connecting local makers with bigger opportunities.
          </p>
        </header>

        {/* Selection Cards Vertical Stack */}
        <div className="mt-8 flex flex-col gap-4">
          {/* Card 1: Seller Flow */}
          <div
            onClick={onSelectSeller}
            className="bg-white rounded-[20px] p-5 shadow-sm border border-gray-100/50 cursor-pointer hover:shadow-md transition-shadow"
          >
            <div className="bg-[#F3EBE1] w-12 h-12 rounded-xl flex items-center justify-center mb-4">
              <User size={24} className="text-[#C47556]" />
            </div>
            <h3 className="text-[#1A2E26] text-xl font-bold">
              I'm a Seller
            </h3>
            <p className="text-slate-500 text-sm mt-1 mb-4">
              Showcase your products and grow your manufacturing business.
            </p>
            <div className="text-[#2A5A4A] text-sm font-semibold flex items-center gap-1">
              <span>Continue as Seller</span>
              <ArrowRight size={16} />
            </div>
          </div>

          {/* Card 2: Buyer Flow */}
          <div
            onClick={onSelectBuyer}
            className="bg-white rounded-[20px] p-5 shadow-sm border border-gray-100/50 cursor-pointer hover:shadow-md transition-shadow"
          >
            <div className="bg-[#E8F1EB] w-12 h-12 rounded-xl flex items-center justify-center mb-4">
              <Building2 size={24} className="text-[#2A5A4A]" />
            </div>
            <h3 className="text-[#1A2E26] text-xl font-bold">
              I'm a Bulk Buyer
            </h3>
            <p className="text-slate-500 text-sm mt-1 mb-4">
              Discover local manufacturers and wholesale products.
            </p>
            <div className="text-[#2A5A4A] text-sm font-semibold flex items-center gap-1">
              <span>Continue as Buyer</span>
              <ArrowRight size={16} />
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="mt-auto pb-8 text-center">
        <span className="text-slate-500 text-sm">
          Already have an account?
        </span>
        <button
          onClick={onSignIn}
          className="text-[#2A5A4A] font-semibold underline ml-1 text-sm bg-transparent border-none cursor-pointer"
        >
          Sign in
        </button>
      </footer>
    </div>
  );
}
