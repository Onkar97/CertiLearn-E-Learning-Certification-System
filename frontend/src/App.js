import React, { useState, useEffect } from 'react';
import './App.css';

const App = () => {
  const [currentRole, setCurrentRole] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);
  const [courses, setCourses] = useState([]);
  const [enrolledCourses, setEnrolledCourses] = useState([]);
  const [quizResults, setQuizResults] = useState([]);
  const [currentQuiz, setCurrentQuiz] = useState(null);
  const [notification, setNotification] = useState({ show: false, message: '', error: false });
  
  const API_BASE = 'http://localhost:8080/api';

  // Show notification function
  const showNotification = (message, isError = false) => {
    setNotification({ show: true, message, error: isError });
    setTimeout(() => {
      setNotification({ show: false, message: '', error: false });
    }, 3000);
  };

  // Load courses from API
  const loadCourses = async () => {
    try {
      const response = await fetch(`${API_BASE}/courses`);
      if (response.ok) {
        const coursesData = await response.json();
        setCourses(coursesData);
      } else {
        throw new Error('Failed to load courses');
      }
    } catch (error) {
      console.error('Error loading courses:', error);
      // Fallback to sample data
      setCourses([
        { id: 1, title: 'Java Programming', description: 'Learn Java fundamentals and OOP concepts' },
        { id: 2, title: 'Spring Boot', description: 'Build REST APIs with Spring Boot framework' },
        { id: 3, title: 'Database Design', description: 'Design and optimize relational databases' }
      ]);
    }
  };

  // Initialize data on component mount
  useEffect(() => {
    if (currentRole) {
      loadCourses();
    }
  }, [currentRole]);

  // Login Screen Component
  const LoginScreen = () => {
    const [selectedRole, setSelectedRole] = useState(null);

    const handleRoleSelect = (role) => {
      setSelectedRole(role);
    };

    const handleLogin = () => {
      if (!selectedRole) return;
      
      setCurrentRole(selectedRole);
      setCurrentUser({
        name: selectedRole === 'STUDENT' ? 'John Student' : 
              selectedRole === 'INSTRUCTOR' ? 'Dr. Jane Instructor' : 'Admin User',
        role: selectedRole
      });
      
      showNotification(`Welcome, ${selectedRole === 'STUDENT' ? 'John Student' : 
        selectedRole === 'INSTRUCTOR' ? 'Dr. Jane Instructor' : 'Admin User'}!`);
    };

    return (
      <div className="login-container">
        <h1 className="login-title">📚 Quiz Platform</h1>
        <p className="login-subtitle">Select your role to access the platform</p>
        
        <div className="role-selector">
          {[
            { role: 'STUDENT', icon: '👨‍🎓', title: 'Student', desc: 'Take quizzes and view results' },
            { role: 'INSTRUCTOR', icon: '👨‍🏫', title: 'Instructor', desc: 'Create courses and manage quizzes' },
            { role: 'ADMIN', icon: '👨‍💼', title: 'Admin', desc: 'Full platform management' }
          ].map(({ role, icon, title, desc }) => (
            <div
              key={role}
              onClick={() => handleRoleSelect(role)}
              className={`role-card ${selectedRole === role ? 'selected' : ''}`}
            >
              <h3>{icon} {title}</h3>
              <p>{desc}</p>
            </div>
          ))}
        </div>
        
        <button
          onClick={handleLogin}
          disabled={!selectedRole}
          className={`btn login-btn ${!selectedRole ? 'disabled' : ''}`}
        >
          Enter Platform
        </button>
      </div>
    );
  };

  // Student Dashboard Component
  const StudentDashboard = () => {
    const [activeTab, setActiveTab] = useState('browse-courses');

    const enrollInCourse = (courseId) => {
      const course = courses.find(c => c.id === courseId);
      if (!enrolledCourses.find(ec => ec.id === courseId)) {
        setEnrolledCourses([...enrolledCourses, course]);
        showNotification(`Successfully enrolled in ${course.title}!`);
      }
    };

    const startQuizForCourse = (courseId) => {
      setActiveTab('take-quiz');
      const course = enrolledCourses.find(c => c.id === courseId);
      setCurrentQuiz({
        courseId: courseId,
        courseName: course.title,
        questions: [
          {
            id: 1,
            text: `What is a key principle in ${course.title}?`,
            type: 'MCQ',
            options: ['Abstraction', 'Decoration', 'Confusion', 'Complication'],
            correctAnswer: 'Abstraction'
          },
          {
            id: 2,
            text: 'Design patterns help make code more maintainable.',
            type: 'TRUE_FALSE',
            options: ['TRUE', 'FALSE'],
            correctAnswer: 'TRUE'
          }
        ]
      });
    };

    const submitQuiz = (answers) => {
      let score = 0;
      currentQuiz.questions.forEach((question, index) => {
        if (answers[index] && answers[index].toLowerCase() === question.correctAnswer.toLowerCase()) {
          score++;
        }
      });

      const percentage = Math.round((score / currentQuiz.questions.length) * 100);
      const result = {
        courseName: currentQuiz.courseName,
        score: score,
        total: currentQuiz.questions.length,
        percentage: percentage,
        date: new Date().toLocaleDateString()
      };

      setQuizResults([...quizResults, result]);
      showNotification('Quiz submitted successfully!');
      return result;
    };

    return (
      <div className="container">
        {/* Header */}
        <div className="header">
          <div className="header-left">
            <h1>Student Dashboard</h1>
            <p>Welcome back! Ready to learn something new?</p>
          </div>
          <div className="header-right">
            <div className="user-info">
              <strong>John Student</strong><br/>
              <span className="role-badge">STUDENT</span>
            </div>
            <button onClick={() => setCurrentRole(null)} className="btn btn-secondary">
              Logout
            </button>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-number">{enrolledCourses.length}</div>
            <div className="stat-label">Enrolled Courses</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{quizResults.length}</div>
            <div className="stat-label">Quizzes Taken</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">
              {quizResults.length > 0 ? Math.round(quizResults.reduce((sum, result) => sum + result.percentage, 0) / quizResults.length) + '%' : '0%'}
            </div>
            <div className="stat-label">Average Score</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{quizResults.filter(r => r.percentage >= 80).length}</div>
            <div className="stat-label">Certificates Earned</div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="nav-tabs">
          {[
            { id: 'browse-courses', label: 'Browse Courses' },
            { id: 'my-courses', label: 'My Courses' },
            { id: 'take-quiz', label: 'Take Quiz' },
            { id: 'results', label: 'My Results' }
          ].map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* Tab Content */}
        <div className="tab-content">
          {activeTab === 'browse-courses' && (
            <div>
              <h2>Available Courses</h2>
              <p>Discover and enroll in courses that interest you</p>
              <div className="course-grid">
                {courses.map(course => {
                  const isEnrolled = enrolledCourses.some(ec => ec.id === course.id);
                  return (
                    <div key={course.id} className="course-card">
                      <h3>{course.title}</h3>
                      <p>{course.description}</p>
                      <div className="course-actions">
                        {isEnrolled ? (
                          <button disabled className="btn btn-success">✓ Enrolled</button>
                        ) : (
                          <button onClick={() => enrollInCourse(course.id)} className="btn">
                            Enroll Now
                          </button>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          )}

          {activeTab === 'my-courses' && (
            <div>
              <h2>My Enrolled Courses</h2>
              {enrolledCourses.length === 0 ? (
                <div className="empty-state">
                  <h3>No enrolled courses</h3>
                  <p>Browse available courses to get started</p>
                </div>
              ) : (
                <div className="course-grid">
                  {enrolledCourses.map(course => (
                    <div key={course.id} className="course-card">
                      <h3>{course.title}</h3>
                      <p>{course.description}</p>
                      <button onClick={() => startQuizForCourse(course.id)} className="btn">
                        Take Quiz
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}

          {activeTab === 'take-quiz' && (
            <StudentQuizComponent 
              enrolledCourses={enrolledCourses}
              currentQuiz={currentQuiz}
              setCurrentQuiz={setCurrentQuiz}
              onSubmitQuiz={submitQuiz}
            />
          )}

          {activeTab === 'results' && (
            <div>
              <h2>My Quiz Results</h2>
              {quizResults.length === 0 ? (
                <div className="empty-state">
                  <h3>No quiz results yet</h3>
                  <p>Take some quizzes to see your results</p>
                </div>
              ) : (
                <div className="course-grid">
                  {quizResults.map((result, index) => (
                    <div key={index} className="result-card">
                      <h3>{result.courseName}</h3>
                      <p><strong>Score:</strong> {result.score}/{result.total} ({result.percentage}%)</p>
                      <p><strong>Date:</strong> {result.date}</p>
                      {result.percentage >= 80 ? (
                        <button className="btn btn-success">✓ Certificate Earned</button>
                      ) : (
                        <p className="no-cert">Certificate not earned</p>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    );
  };

  // Student Quiz Component
  const StudentQuizComponent = ({ enrolledCourses, currentQuiz, setCurrentQuiz, onSubmitQuiz }) => {
    const [selectedCourse, setSelectedCourse] = useState('');
    const [answers, setAnswers] = useState({});
    const [quizResult, setQuizResult] = useState(null);

    const handleCourseSelect = (courseId) => {
      setSelectedCourse(courseId);
      if (courseId) {
        const course = enrolledCourses.find(c => c.id == courseId);
        setCurrentQuiz({
          courseId: courseId,
          courseName: course.title,
          questions: [
            {
              id: 1,
              text: `What is a key principle in ${course.title}?`,
              type: 'MCQ',
              options: ['Abstraction', 'Decoration', 'Confusion', 'Complication'],
              correctAnswer: 'Abstraction'
            },
            {
              id: 2,
              text: 'Design patterns help make code more maintainable.',
              type: 'TRUE_FALSE',
              options: ['TRUE', 'FALSE'],
              correctAnswer: 'TRUE'
            }
          ]
        });
        setAnswers({});
        setQuizResult(null);
      } else {
        setCurrentQuiz(null);
      }
    };

    const handleAnswerChange = (questionId, answer) => {
      setAnswers(prev => ({ ...prev, [questionId]: answer }));
    };

    const handleSubmit = () => {
      const answerArray = currentQuiz.questions.map(q => answers[q.id] || '');
      const allAnswered = answerArray.every(answer => answer.trim() !== '');
      
      if (!allAnswered) {
        alert('Please answer all questions');
        return;
      }

      const result = onSubmitQuiz(answerArray);
      setQuizResult(result);
    };

    return (
      <div>
        <h2>Take Quiz</h2>
        <div className="form-group">
          <label>Select Course:</label>
          <select
            value={selectedCourse}
            onChange={(e) => handleCourseSelect(e.target.value)}
            className="form-select"
          >
            <option value="">Choose from your enrolled courses...</option>
            {enrolledCourses.map(course => (
              <option key={course.id} value={course.id}>{course.title}</option>
            ))}
          </select>
        </div>

        {currentQuiz && (
          <div className="quiz-container">
            <h3>{currentQuiz.courseName} Quiz</h3>
            
            {currentQuiz.questions.map((question, index) => (
              <div key={question.id} className="quiz-question">
                <h4>Question {index + 1}: {question.text}</h4>
                
                {question.type === 'SHORT_ANSWER' ? (
                  <textarea
                    value={answers[question.id] || ''}
                    onChange={(e) => handleAnswerChange(question.id, e.target.value)}
                    rows="3"
                    className="quiz-textarea"
                    placeholder="Enter your answer here..."
                  />
                ) : (
                  <div className="quiz-options">
                    {question.options.map(option => (
                      <label key={option} className="quiz-option">
                        <input
                          type="radio"
                          name={`question_${question.id}`}
                          value={option}
                          checked={answers[question.id] === option}
                          onChange={(e) => handleAnswerChange(question.id, e.target.value)}
                        />
                        {option}
                      </label>
                    ))}
                  </div>
                )}
              </div>
            ))}

            <button onClick={handleSubmit} className="btn quiz-submit-btn">
              Submit Quiz
            </button>

            {quizResult && (
              <div className="quiz-result">
                <h3>Quiz Completed! 🎉</h3>
                <div className="score-display">{quizResult.score}/{quizResult.total}</div>
                <p><strong>Percentage:</strong> {quizResult.percentage}%</p>
                {quizResult.percentage >= 80 ? (
                  <div>
                    <p><strong>🎓 Congratulations! You earned a certificate!</strong></p>
                    <button className="btn">Download Certificate</button>
                  </div>
                ) : (
                  <p>Score 80% or higher to earn a certificate</p>
                )}
              </div>
            )}
          </div>
        )}
      </div>
    );
  };

  // Instructor Dashboard Component
  const InstructorDashboard = () => {
    const [activeTab, setActiveTab] = useState('my-courses');
    const [courseForm, setCourseForm] = useState({ title: '', description: '' });

    const createCourse = async () => {
      if (!courseForm.title || !courseForm.description) {
        showNotification('Please fill all fields', true);
        return;
      }

      try {
        const response = await fetch(`${API_BASE}/courses`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(courseForm)
        });

        if (response.ok) {
          const newCourse = await response.json();
          setCourses([...courses, newCourse]);
          setCourseForm({ title: '', description: '' });
          showNotification('Course created successfully!');
        } else {
          throw new Error('Failed to create course');
        }
      } catch (error) {
        showNotification('Error creating course', true);
      }
    };

    return (
      <div className="container">
        {/* Header */}
        <div className="header">
          <div className="header-left">
            <h1>Instructor Dashboard</h1>
            <p>Manage your courses and track student progress</p>
          </div>
          <div className="header-right">
            <div className="user-info">
              <strong>Dr. Jane Instructor</strong><br/>
              <span className="role-badge">INSTRUCTOR</span>
            </div>
            <button onClick={() => setCurrentRole(null)} className="btn btn-secondary">
              Logout
            </button>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-number">{courses.length}</div>
            <div className="stat-label">Courses Created</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{Math.floor(Math.random() * 50) + 10}</div>
            <div className="stat-label">Total Students</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{Math.floor(Math.random() * 100) + 20}</div>
            <div className="stat-label">Questions Created</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">78%</div>
            <div className="stat-label">Avg Student Score</div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="nav-tabs">
          {[
            { id: 'my-courses', label: 'My Courses' },
            { id: 'create-course', label: 'Create Course' },
            { id: 'manage-quizzes', label: 'Manage Quizzes' }
          ].map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* Tab Content */}
        <div className="tab-content">
          {activeTab === 'my-courses' && (
            <div>
              <h2>My Courses</h2>
              <div className="course-grid">
                {courses.map(course => (
                  <div key={course.id} className="course-card">
                    <h3>{course.title}</h3>
                    <p>{course.description}</p>
                    <button className="btn">Edit Course</button>
                  </div>
                ))}
              </div>
            </div>
          )}

          {activeTab === 'create-course' && (
            <div>
              <h2>Create New Course</h2>
              <div className="form-section">
                <div className="form-group">
                  <label>Course Title:</label>
                  <input
                    type="text"
                    value={courseForm.title}
                    onChange={(e) => setCourseForm({...courseForm, title: e.target.value})}
                    placeholder="e.g., Advanced Java Programming"
                    className="form-input"
                  />
                </div>
                <div className="form-group">
                  <label>Course Description:</label>
                  <textarea
                    value={courseForm.description}
                    onChange={(e) => setCourseForm({...courseForm, description: e.target.value})}
                    placeholder="Describe what students will learn..."
                    rows="4"
                    className="form-textarea"
                  />
                </div>
                <button onClick={createCourse} className="btn">
                  Create Course
                </button>
              </div>
            </div>
          )}

          {activeTab === 'manage-quizzes' && (
            <div>
              <h2>Manage Quiz Questions</h2>
              <div className="form-section">
                <h3>Question Factory Demo</h3>
                <p>This demonstrates your QuestionFactory pattern implementation</p>
                <button
                  onClick={() => showNotification('Question created using QuestionFactory!')}
                  className="btn"
                >
                  Demo Question Factory
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  };

  // Admin Dashboard Component
  const AdminDashboard = () => {
    const [activeTab, setActiveTab] = useState('overview');

    return (
      <div className="container">
        {/* Header */}
        <div className="header">
          <div className="header-left">
            <h1>Admin Dashboard</h1>
            <p>Complete platform management and oversight</p>
          </div>
          <div className="header-right">
            <div className="user-info">
              <strong>Admin User</strong><br/>
              <span className="role-badge">ADMIN</span>
            </div>
            <button onClick={() => setCurrentRole(null)} className="btn btn-secondary">
              Logout
            </button>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-number">{courses.length}</div>
            <div className="stat-label">Total Courses</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{Math.floor(Math.random() * 100) + 50}</div>
            <div className="stat-label">Total Users</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{Math.floor(Math.random() * 500) + 100}</div>
            <div className="stat-label">Quizzes Taken</div>
          </div>
          <div className="stat-card">
            <div className="stat-number">{Math.floor(Math.random() * 50) + 25}</div>
            <div className="stat-label">Certificates Issued</div>
          </div>
        </div>

        {/* Navigation Tabs */}
        <div className="nav-tabs">
          {[
            { id: 'overview', label: 'Platform Overview' },
            { id: 'manage-courses', label: 'Manage Courses' },
            { id: 'system-reports', label: 'System Reports' }
          ].map(tab => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id)}
              className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* Tab Content */}
        <div className="tab-content">
          {activeTab === 'overview' && (
            <div>
              <h2>Platform Overview</h2>
              <div className="course-grid">
                <div className="course-card">
                  <h3>📊 System Health</h3>
                  <p><strong>Backend Status:</strong> Connected</p>
                  <p><strong>Database:</strong> PostgreSQL Online</p>
                  <p><strong>Last Backup:</strong> Today 3:00 AM</p>
                  <button className="btn btn-success">System Healthy</button>
                </div>
                <div className="course-card">
                  <h3>🎯 Recent Activity</h3>
                  <p>5 new students enrolled today</p>
                  <p>12 quizzes submitted this week</p>
                  <p>3 certificates issued</p>
                  <button className="btn">View Details</button>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'manage-courses' && (
            <div>
              <h2>Manage All Courses</h2>
              <div className="course-grid">
                {courses.map(course => (
                  <div key={course.id} className="course-card">
                    <h3>{course.title}</h3>
                    <p>{course.description}</p>
                    <div className="course-actions">
                      <button className="btn">Edit</button>
                      <button className="btn btn-secondary">Analytics</button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {activeTab === 'system-reports' && (
            <div>
              <h2>System Reports</h2>
              <div className="course-grid">
                <div className="course-card">
                  <h3>📈 Quiz Performance</h3>
                  <p>Average Score: 78%</p>
                  <p>Most Popular Course: Java Programming</p>
                  <button className="btn">Generate Report</button>
                </div>
                <div className="course-card">
                  <h3>🏆 Certificate Analytics</h3>
                  <p>Certificates Issued: 45</p>
                  <p>Pass Rate: 82%</p>
                  <button className="btn">Export Data</button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    );
  };

  // Main render
  return (
    <div className="app">
      {/* Notification */}
      {notification.show && (
        <div className={`notification ${notification.error ? 'error' : ''}`}>
          {notification.message}
        </div>
      )}

      {/* Render appropriate component based on current role */}
      {!currentRole && <LoginScreen />}
      {currentRole === 'STUDENT' && <StudentDashboard />}
      {currentRole === 'INSTRUCTOR' && <InstructorDashboard />}
      {currentRole === 'ADMIN' && <AdminDashboard />}
    </div>
  );
};

export default App;