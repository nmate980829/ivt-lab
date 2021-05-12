package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  @Mock
  TorpedoStore primary;
  @Mock
  TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);
    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(0)).isEmpty();
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_Success_Secondary(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Success_Alternating(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertEquals(true, result);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_Single_Failure_Firing_Fails(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(0)).isEmpty();
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Failure_Empty_Bays(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_All_Success_First_Bay_Empty(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Failure_Primary_Empty_Secondary_Error(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_Success_Alternating_Secondary_Empty(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(2)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(2)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(true, result);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_Single_Alternating_Secondary_Empty_First_Empty_On_Second_Try(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(2)).isEmpty();
    verify(secondary, times(1)).isEmpty();
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertEquals(true, result);
    assertEquals(false, result2);
  }
}
