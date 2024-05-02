import './App.css';
import ForgotPassword from './pages/PatientPasswordRecovery/ForgotPassword';
import ResetPasswordForm from './pages/PatientPasswordRecovery/ResetPasswordForm';
import PatientSignUp from "./pages/LoginSignUp/PatientSignUp";
import WelcomePage from "./pages/WelcomePage";
import {Routes, Route} from 'react-router-dom';
import DoctorRegistration from "./pages/LoginSignUp/DoctorRegistration";
import PharmacyRegistration from "./pages/LoginSignUp/PharmacyRegistration";
import SignIn from "./pages/LoginSignUp/Login";
import PatientBookAppoinmet from "./pages/Patient/PatientBookAppoinment";
import PatientProfile from './pages/Patient/PatientProfile';
import AdminDashboard from './pages/Admin/AdminDashboard';
import SingleDoctorRequest from './pages/Admin/SingleDoctorRequest';
import SinglePharmacistRequest from './pages/Admin/SinglePharmacistRequest';
import PersonalInfo from "./pages/DoctorProfile/PersonalDetails";
import AvailabilityDetails from "./pages/DoctorProfile/Availability";
import ConsultationDetails from "./pages/DoctorProfile/Consultation";
import PharmacyDetails from "./pages/DoctorProfile/Pharmacy";
import AppoinmentDetails from "./pages/DoctorProfile/Appoinment";
import PlacePharmacyOrder from "./pages/DoctorProfile/PlacePharmacyOrder";
import NotificationPreferences from "./pages/DoctorProfile/NotificationPrefrences";
import ChangePassword from "./pages/DoctorProfile/ChangePassword";
import PatientDashboard from './pages/Patient/PatientDashboard';
import DoctorCatalog from './pages/Patient/DoctorCatalog';
import DoctorHome from './pages/DoctorProfile/DoctorHome'
import BlogPageDoctor from "./pages/DoctorProfile/BlogPageDoctor";
import BlogPagePatient from "./pages/Patient/BlogPagePatient";
import CreateBlog from "./pages/DoctorProfile/CreateBlog";
import Appointments from "./pages/Patient/Appointments";
import DoctorFeedback from "./pages/DoctorProfile/DoctorFeedback";
import PharmacyProfile from "./pages/Pharmacy/PharmacyProfile";
import Prescriptions from "./pages/Pharmacy/Prescriptions";
import ChangePasswordPharmacy from "./pages/Pharmacy/ChangePassword";
import ChatRoot from './pages/Chat/ChatRoot';



function App() {
  return (
      <Routes>
          <Route exact path="/" element={<WelcomePage/>} />
          <Route path="/doctor-registration" element={<DoctorRegistration />} />
          <Route path="/pharmacy-registration" element={<PharmacyRegistration />} />
          <Route path="/sign-in" element={<SignIn />} />
          <Route path="/sign-up" element={<PatientSignUp />} />
          {/* <Route path="/patient-profile" element={<PatientProfile />} /> */}
          <Route path="/forgot-password" element={<ForgotPassword/>}/>
          <Route path="/reset-password" element={<ResetPasswordForm/>} />
          <Route path="/patient/dashboard" element={<PatientDashboard/>} />
          <Route path="/patient/profile" element={<PatientProfile/>} />
          <Route path="/patient/book-appointment/:id" element={<PatientBookAppoinmet/>} />
          <Route path="/patient/chat" element={<ChatRoot isPatient={true}/>} />
          <Route path="/patient/appointment" element={<Appointments/>} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route path="/admin/doctor-pending-request/:username" element={<SingleDoctorRequest />} />
          <Route path="/admin/pharmacist-pending-request/:username" element={<SinglePharmacistRequest/>} />
          <Route path="/doctor-personal-details" element={<PersonalInfo/>} />
          <Route path="/doctor-availability" element={<AvailabilityDetails/>} />
          <Route path="/doctor-consultation" element={<ConsultationDetails/>} />
          <Route path="/doctor-pharmacy" element={<PharmacyDetails/>} />
          <Route path="/doctor-pharmacy/place-order" element={<PlacePharmacyOrder/>} />
          <Route path="/doctor-appointment" element={<AppoinmentDetails/>} />
          <Route path="/doctor-catalog" element={<DoctorCatalog/>}/>
          <Route path="/doctor-Home" element={<DoctorHome/>}/>
          <Route path ="/doctor-notification" element={<NotificationPreferences/>}/>
          <Route path ="/doctor-change-password" element={<ChangePassword/>}/>
          <Route path="/doctor/chat" element={<ChatRoot isPatient={false}/>} />
          <Route path="/blog-page-doctor" element={<BlogPageDoctor/>}/>
          <Route path="/blog-page-patient" element={<BlogPagePatient/>}/>
          <Route path="/create-blog" element={<CreateBlog/>}/>
          <Route path="/pharmacy-home" element={<PharmacyProfile/>}/>
          <Route path="/pharmacy/Prescription" element={<Prescriptions/>}/>
          <Route path="/pharmacy/Change-password" element={<ChangePasswordPharmacy/>}/>
          <Route path="/feedback" element={<DoctorFeedback/>}/>

      </Routes>

    );
}

export default App;
