import React, { useState, useEffect } from 'react';
import SplashScreen from './SplashScreen';
import RoleSelection from './RoleSelection';
import SellerRegistration from './SellerRegistration';
import BuyerRegistration from './BuyerRegistration';
import SignIn from './SignIn';

export default function App() {
  const [currentScreen, setCurrentScreen] = useState('splash');

  useEffect(() => {
    const timer = setTimeout(() => {
      setCurrentScreen(prev => (prev === 'splash' ? 'roleSelection' : prev));
    }, 2500);
    return () => clearTimeout(timer);
  }, []);

  if (currentScreen === 'splash') {
    return (
      <div onClick={() => setCurrentScreen('roleSelection')} className="cursor-pointer">
        <SplashScreen />
      </div>
    );
  }

  if (currentScreen === 'sellerRegistration') {
    return (
      <SellerRegistration
        onBack={() => setCurrentScreen('roleSelection')}
        onSubmitSuccess={(data) => {
          alert(`Welcome ${data.fullName}! Seller account created.`);
          setCurrentScreen('roleSelection');
        }}
      />
    );
  }

  if (currentScreen === 'buyerRegistration') {
    return (
      <BuyerRegistration
        onBack={() => setCurrentScreen('roleSelection')}
        onSubmitSuccess={(data) => {
          alert(`Welcome ${data.fullName}! Buyer account created.`);
          setCurrentScreen('roleSelection');
        }}
      />
    );
  }
  if (currentScreen === 'signIn') {
    return (
      <SignIn
        onBack={() => setCurrentScreen('roleSelection')}
        onSubmitSuccess={(data) => {
          alert(`Signed in as ${data.activeRole}!`);
          setCurrentScreen('roleSelection');
        }}
        onForgotPassword={() => alert('Password reset link sent to mobile!')}
      />
    );
  }
  return (
    <RoleSelection
      onSelectSeller={() => setCurrentScreen('sellerRegistration')}
      onSelectBuyer={() => setCurrentScreen('buyerRegistration')}
      onSignIn={() => setCurrentScreen('signIn')}
    />
  );
}
