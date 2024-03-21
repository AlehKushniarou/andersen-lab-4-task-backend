package lab.andersen;

import lab.andersen.dao.UserActivityDao;
import lab.andersen.dto.CreateUserActivityDto;
import lab.andersen.dto.UserActivityDto;
import lab.andersen.dto.UserActivityShortDto;
import lab.andersen.entity.UserActivity;
import lab.andersen.exception.DaoException;
import lab.andersen.exception.ServiceException;
import lab.andersen.service.UserActivityService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserActivityUnitTest {

    @Mock
    private UserActivityDao dao;

    @InjectMocks
    private UserActivityService service;

    public UserActivityUnitTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void findAllTodayActivities() throws Exception {
        Date date = new Date();
        List<UserActivityShortDto> expectedActivities = new ArrayList<>();
        expectedActivities.add(new UserActivityShortDto(1, "username1", "test1", new Timestamp(date.getTime())));
        expectedActivities.add(new UserActivityShortDto(1, "username2", "test2", new Timestamp(date.getTime())));
        when(dao.findAllToday()).thenReturn(expectedActivities);
        List<UserActivityShortDto> actual = service.findAllTodayActivities();
        assertEquals(expectedActivities, actual);
        verify(dao, times(1)).findAllToday();
    }

    @Test
    public void findAllTodayActivitiesThrowsException() throws Exception {
        when(dao.findAllToday()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> service.findAllTodayActivities());
        verify(dao, times(1)).findAllToday();
    }

    @Test
    public void findById() throws Exception {
        UserActivity expectedUserActivity = new UserActivity(1, 1, "test1", Timestamp.valueOf("2024-01-01 01:01:01"));
        UserActivityDto expectedDto = new UserActivityDto(expectedUserActivity.getUserId(), expectedUserActivity.getDescription(), expectedUserActivity.getDateTime());
        when(dao.findById(expectedUserActivity.getId())).thenReturn(Optional.ofNullable(expectedUserActivity));
        UserActivityDto actualDto = service.findById(expectedUserActivity.getId());
        assertEquals(expectedDto, actualDto);
        verify(dao, times(1)).findById(expectedUserActivity.getId());
    }

    @Test
    public void findByIdThrowsException() throws Exception {
        when(dao.findById(anyInt())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> service.findById(anyInt()));
        verify(dao, times(1)).findById(anyInt());
    }

    @Test
    public void create() throws Exception {
        int numberOfRowsAffected = 1;
        String name = "test1";
        CreateUserActivityDto dto = new CreateUserActivityDto("description1");
        when(dao.create(name, dto.getDescription())).thenReturn(numberOfRowsAffected);
        assertEquals(numberOfRowsAffected, service.create(name, dto));
        verify(dao, times(1)).create(name, dto.getDescription());
    }

    @Test
    public void createThrowsException() throws Exception {
        String name = "test1";
        CreateUserActivityDto dto = new CreateUserActivityDto("description1");
        when(dao.create(name, dto.getDescription())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, ()->service.create(name, dto));
        verify(dao, times(1)).create(anyString(), anyString());
    }

//
//    @Test
//    public void updateUserActivity() throws Exception {
//        UserActivity expectedUserActivity = new UserActivity(1, 1, "test1", Timestamp.valueOf("2024-01-01 09:01:15"));
//        when(dao.update(expectedUserActivity)).thenReturn(expectedUserActivity);
//        UserActivity actualUserActivity = service.update(expectedUserActivity);
//        assertEquals(actualUserActivity, expectedUserActivity);
//        verify(dao, times(1)).update(expectedUserActivity);
//    }
//
//    @Test
//    public void updateUserActivityThrowsException() throws Exception {
//        UserActivity activity = new UserActivity();
//        doThrow(DaoException.class).when(dao).update(activity);
//        assertThrows(ServiceException.class, () -> service.update(activity));
//        verify(dao, times(1)).update(activity);
//    }
//
//    @Test
//    public void createActivities() throws Exception {
//        UserActivity expectedActivity = new UserActivity();
//        when(dao.create(expectedActivity)).thenReturn(expectedActivity);
//        UserActivity actualActivity = service.create(expectedActivity);
//        assertEquals(expectedActivity, actualActivity);
//        verify(dao, times(1)).create(expectedActivity);
//    }
//
//    @Test
//    public void createActivitiesThrowsException() throws Exception {
//        UserActivity activity = new UserActivity();
//        doThrow(DaoException.class).when(dao).create(any(UserActivity.class));
//        assertThrows(ServiceException.class, () -> service.create(activity));
//        verify(dao, times(1)).create(activity);
//    }
//
//    @Test
//    public void deleteActivity() throws Exception {
//        int id = 1;
//        doNothing().when(dao).delete(id);
//        service.delete(id);
//        verify(dao, times(1)).delete(id);
//    }
//
//    @Test
//    public void deleteActivityThrowsException() throws Exception {
//        int id = 1;
//        doThrow(DaoException.class).when(dao).delete(anyInt());
//        assertThrows(ServiceException.class, () -> service.delete(id));
//        verify(dao, times(1)).delete(id);
//    }
}
