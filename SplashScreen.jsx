import React from 'react';
import { Home } from 'lucide-react';

export default function SplashScreen() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-[#F9F8F4] px-4">
      {/* Logo Icon */}
      <div className="w-24 h-24 rounded-2xl bg-[#2A5A4A] shadow-md flex items-center justify-center">
        <Home size={40} color="white" strokeWidth={1.5} />
      </div>

      {/* App Title */}
      <h1 className="text-[#2A5A4A] text-3xl font-bold tracking-tight mt-6">
        Kutira-Kushala
      </h1>

      {/* Tagline */}
      <p className="text-[#C47556] text-base mt-2 font-medium">
        Local Makers. Limitless Possibilities.
      </p>
    </div>
  );
}
