import SwiftUI
import Combine
import Shared


class TimerListViewModel: ObservableObject {
    @Published var timers: [SharedTimer] = []

    func loadTimers() {
        timers = TimerRepository().getTimers()
    }
}


struct ContentView: View {
    
    @StateObject private var viewModel = TimerListViewModel()

    var body: some View {
        List(viewModel.timers, id: \.id) { timer in
            TimerItemView(timer: timer)
        }
        .onAppear {
            viewModel.loadTimers()
        }
    }
}

struct TimerItemView: View {
    @ObservedObject var timer: TimerWrapper

    init(timer: SharedTimer) {
            self.timer = TimerWrapper.getInstance(for: timer)
    }

    var body: some View {
        HStack {
                    Text("Timer \(timer.timer.id): \(timer.time)")

                    Button(action: {
                        
                    }) {
                        Text("Start")
                    }
                    .onTapGesture {
                        timer.start()
                    }
                    .padding()
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(8)

                    Button(action: {
                        
                    }) {
                        Text("Stop")
                    }
                    .onTapGesture {
                        timer.stop()
                    }
                    .padding()
                    .background(Color.red)
                    .foregroundColor(.white)
                    .cornerRadius(8)
                }
                .padding()
    }
}

class TimerWrapper: ObservableObject {
    let timer: SharedTimer
    @Published var time: Int = 0

    private init(timer: SharedTimer) {
        self.timer = timer
        self.subscribeToTimerUpdates()
    }
    
    private func subscribeToTimerUpdates() {
        print("Subscribing to updates for timer \(timer.id)")
             
             // Assuming `addValueUpdateHandler` registers a callback and returns a subscription/cancellation token
             timer.addValueUpdateHandler { [weak self] value in
                 print("Received update for \(self?.timer.id ?? -1): \(value)")
                 DispatchQueue.main.async {
                     self?.time = Int(truncating: value as NSNumber)
                 }
             }
    }
    
    // Use a shared instance dictionary to cache instances
    private static var cache = [Int: TimerWrapper]()

    static func getInstance(for timer: SharedTimer) -> TimerWrapper {
        let id = Int(timer.id)
        if let instance = cache[id] {
                return instance
            } else {
                let instance = TimerWrapper(timer: timer)
                cache[id] = instance
                return instance
            }
    }


    func start() {
        print("Start button clicked")
        timer.start()
    }

    func stop() {
        print("Stop button clicked")
        timer.stop()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
