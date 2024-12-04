import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    specPattern: 'cypress/e2e/**/*.spec.ts', // Path to your spec files
    supportFile: false, // Disable the support file
    baseUrl: 'http://localhost:4200', // Your app's base URL
  },
});
